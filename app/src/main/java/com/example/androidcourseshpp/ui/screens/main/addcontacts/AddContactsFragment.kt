package com.example.androidcourseshpp.ui.screens.main.addcontacts


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidcourseshpp.data.userlist.UserItem
import com.example.androidcourseshpp.databinding.FragmentAddContactsBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter.UserItemActions
import com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContactsFragment : BaseFragment<FragmentAddContactsBinding>
    (FragmentAddContactsBinding::inflate) {

    private val viewModel by viewModels<AddContactsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        setObservers()
        setListeners()
    }

    private fun initRecyclerView() = with(binding.recyclerViewUsers) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = UsersAdapter(object : UserItemActions {
            override fun addToContacts(userItem: UserItem) {

            }
        })

    }

    override fun setObservers() {

    }

    override fun setListeners() {

    }

}