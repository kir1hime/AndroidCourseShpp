package com.example.androidcourseshpp.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewbinding.ViewBinding
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.ui.screens.MainActivity
import com.example.androidcourseshpp.ui.screens.UserInfoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

const val USER_INFO = "userInfo"

open class BaseFragment<VBinding : ViewBinding>(private val inflaterMethod: (LayoutInflater, ViewGroup?, Boolean) -> VBinding) :
    Fragment() {

    private var _binding: VBinding? = null
     val binding get() = requireNotNull(_binding)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflaterMethod.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun <T> BaseFragment<VBinding>.collectFlow(flow: Flow<T>, onCollect: (T) -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collect {
                    onCollect(it)
                }
            }
        }
    }

    fun makeToast(messageResId: Int) {
        Toast.makeText(requireContext(), messageResId, Toast.LENGTH_LONG).show()
    }

    fun moveToMyProfileScreen(userInfo: UserInfoEntity) {
        val intent = Intent(requireContext(), MainActivity::class.java)

        intent.putExtras(bundleOf(USER_INFO to userInfo))

        val options = ActivityOptions.makeCustomAnimation(
            requireContext(),
            R.anim.slide_in_from_right_to_left,
            R.anim.slide_out_from_right_to_left
        )

        startActivity(intent, options.toBundle())
        requireActivity().finish()
    }
}