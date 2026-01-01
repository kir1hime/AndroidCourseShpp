package com.example.androidcourseshpp.ui.screens.main.addcontacts


import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.models.userlist.UserItem
import com.example.androidcourseshpp.databinding.FragmentAddContactsBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter.UserItemActions
import com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter.UserItemDecorations
import com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint

const val TO_RELOAD_CONTACT_LIST = "reloadContactList"

@AndroidEntryPoint
class AddContactsFragment : BaseFragment<FragmentAddContactsBinding>
    (FragmentAddContactsBinding::inflate) {

    private val viewModel by viewModels<AddContactsViewModel>()

    private lateinit var sharedUserProfilePhoto: ImageView
    private val adapter by lazy {
        UsersAdapter(object : UserItemActions {
            override fun addToContacts(userItem: UserItem, interruptLoading: () -> Unit) {
                viewModel.setEvent(
                    AddContactsContract.Event.OnAddContactClicked(
                        userItem,
                        interruptLoading
                    )
                )
            }

            override fun showUserItemDetails(userItem: UserItem, avatar: ImageView) {
                sharedUserProfilePhoto = avatar
                viewModel.setEvent(AddContactsContract.Event.OnUserItemClicked(userItem))
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        setObservers()
        setListeners()
    }

    private fun initRecyclerView() = with(binding.recyclerViewUsers) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = this@AddContactsFragment.adapter

        addItemDecoration(
            UserItemDecorations(resources.getDimensionPixelSize(R.dimen.contacts_recycle_view_space_size_between_items))
        )
    }

    override fun setObservers() = with(binding) {
        collectFlow(viewModel.state) { state ->
            val userList = state.userList
            adapter.submitList(userList)
            progressBarRequest.isVisible = state.isProgressBarShowed
            buttonTryAgain.isVisible = state.isTryAgainButtonShowed
        }

        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is AddContactsContract.Effect.NavigateToContactListScreen -> moveToContactList(
                    effect.isContactListChanged
                )

                is AddContactsContract.Effect.ShowToast -> makeToast(effect.toastMessageResId)
                is AddContactsContract.Effect.NavigateToDetailsScreen -> moveToDetailsScreen(
                    effect.userItem
                )
            }
        }
    }

    override fun setListeners() = with(binding) {
        imageButtonArrowBack.setOnClickListener {
            viewModel.setEvent(AddContactsContract.Event.OnArrowBackButtonClicked)
        }
        buttonTryAgain.setOnClickListener {
            viewModel.setEvent(AddContactsContract.Event.LoadUserList)
        }
    }

    private fun moveToDetailsScreen(userItem: UserItem) {
        val extras =
            FragmentNavigatorExtras(sharedUserProfilePhoto to userItem.id.toString())

        val direction =
            AddContactsFragmentDirections.actionAddContactsFragmentToContactDetailsFragment(
                userItem.toContactDetailsEntity()
            )

        findNavController().navigate(direction, extras)
    }

    private fun moveToContactList(isContactListChanged: Boolean) {
        if (isContactListChanged) {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                TO_RELOAD_CONTACT_LIST,
                true
            )
        }
        findNavController().navigateUp()
    }
}