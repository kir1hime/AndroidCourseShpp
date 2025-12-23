package com.example.androidcourseshpp.ui.screens.main.addcontacts


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.androidcourseshpp.databinding.FragmentAddContactsBinding
import com.example.androidcourseshpp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactsFragment : BaseFragment<FragmentAddContactsBinding>
    (FragmentAddContactsBinding::inflate) {

    private val viewModel by viewModels<AddContactsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setListeners()
    }

    override fun setObservers() {

    }

    override fun setListeners() {

    }

}