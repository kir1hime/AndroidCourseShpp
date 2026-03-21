package com.example.androidcourseshpp.ui.screens.main.addcontacts


import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.databinding.FragmentAddContactsBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter.UserItemActions
import com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter.UserItemDecorations
import com.example.androidcourseshpp.ui.screens.main.addcontacts.adapter.UsersAdapter
import com.example.androidcourseshpp.ui.screens.main.addcontacts.model.UserItem
import com.example.androidcourseshpp.ui.screens.main.contactdetails.REQUEST_CODE
import com.example.androidcourseshpp.ui.screens.main.contactdetails.TO_RELOAD_USER_LIST
import com.example.androidcourseshpp.ui.utils.onChangeTextListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddContactsFragment : BaseFragment<FragmentAddContactsBinding>
    (FragmentAddContactsBinding::inflate) {

    private val viewModel by viewModels<AddContactsViewModel>()

    private lateinit var sharedUserProfilePhoto: ImageView
    private val adapter by lazy {
        UsersAdapter(object : UserItemActions {
            override fun addToContacts(
                userItem: UserItem,
                interruptSuccessLoading: () -> Unit,
                interruptFailureLoading: () -> Unit
            ) {
                viewModel.setEvent(
                    AddContactsContract.Event.OnAddContactClicked(
                        userItem.toContactDetails(),
                        interruptSuccessLoading,
                        interruptFailureLoading
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
        setUserListReloadListener()
        initRecyclerView()
        setObservers()
        setListeners()
    }

    private fun setUserListReloadListener() {
        parentFragmentManager.setFragmentResultListener(
            REQUEST_CODE,
            viewLifecycleOwner
        ) { _, data ->
            val userId = data.getInt(TO_RELOAD_USER_LIST)
            val updatedList = viewModel.state.value.userList.toMutableList()
            updatedList.map { user ->
                if (user.id == userId) {
                    user.isContact = true
                }
            }
            adapter.submitList(updatedList)
        }
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
            if (state.isSearchMode) {
                showSearchBar()
            }
            if (!state.isSearchMode) {
                val userList = state.userList
                adapter.submitList(userList)
            }

            progressBarRequest.isVisible = state.isProgressBarShowed
            buttonTryAgain.isVisible = state.isTryAgainButtonShowed
            imageButtonSearch.isClickable = !progressBarRequest.isVisible
            floatingButtonArrowTop.isVisible =
                !progressBarRequest.isVisible && !state.isTryAgainButtonShowed

        }

        collectFlow(viewModel.filteredUserList) { filteredUserList ->
            if (filteredUserList.isEmpty() && textInputLayoutSearch.isVisible) {
                textViewNoResultsFound.isVisible = true
                textViewAdvice.isVisible = true
            } else {
                textViewNoResultsFound.isVisible = false
                textViewAdvice.isVisible = false
            }
            if (textInputLayoutSearch.isVisible) {
                adapter.submitList(filteredUserList)
            }
        }

        var toast: Toast? = null
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is AddContactsContract.Effect.ScrollUserListToTop -> scrollUserListToTop()
                is AddContactsContract.Effect.ShowSearchBar -> showSearchBar()
                is AddContactsContract.Effect.HideSearchBar -> hideSearchBar()
                is AddContactsContract.Effect.NavigateToContactListScreen -> moveToContactList()

                is AddContactsContract.Effect.NavigateToDetailsScreen -> moveToDetailsScreen(
                    effect.userItem
                )

                is AddContactsContract.Effect.ShowToast -> {
                    toast?.cancel()
                    toast = makeToast(effect.toastMessageResId)
                    toast.show()
                }
            }
        }
    }

    private fun scrollUserListToTop() = with(binding) {
        recyclerViewUsers.scrollToPosition(
            0
        )
    }

    private fun hideSearchBar() = with(binding) {
        editTextSearch.setText("")
        textInputLayoutSearch.isVisible = false
        imageButtonSearch.isVisible = true
        imageButtonHideSearch.isVisible = false
    }

    private fun showSearchBar() = with(binding) {
        textInputLayoutSearch.isVisible = true
        imageButtonSearch.isVisible = false
        imageButtonHideSearch.isVisible = true
    }

    override fun setListeners() = with(binding) {
        imageButtonArrowBack.setOnClickListener {
            viewModel.setEvent(AddContactsContract.Event.OnArrowBackButtonClicked)
        }
        buttonTryAgain.setOnClickListener {
            viewModel.setEvent(AddContactsContract.Event.LoadUserList)
        }
        imageButtonSearch.setOnClickListener {
            viewModel.setEvent(AddContactsContract.Event.SearchModeSwitched(true))
            viewModel.setEvent(AddContactsContract.Event.OnSearchButtonClicked)
        }
        imageButtonHideSearch.setOnClickListener {
            viewModel.setEvent(AddContactsContract.Event.SearchModeSwitched(false))
            viewModel.setEvent(AddContactsContract.Event.OnHideSearchButtonClicked)
        }
        floatingButtonArrowTop.setOnClickListener {
            viewModel.setEvent(AddContactsContract.Event.OnArrowTopFloatingButtonClicked)
        }
        editTextSearch.onChangeTextListener { sequence, _, _, _ ->
            viewModel.setEvent(AddContactsContract.Event.OnSearchBarTextChanged(sequence.toString()))
        }
    }

    private fun moveToDetailsScreen(userItem: UserItem) {
        val extras =
            FragmentNavigatorExtras(sharedUserProfilePhoto to userItem.id.toString())

        val direction =
            AddContactsFragmentDirections.actionAddContactsFragmentToContactDetailsFragment(
                userItem.toContactDetails(), true
            )

        findNavController().navigate(direction, extras)
    }

    private fun moveToContactList() {
        findNavController().navigateUp()
    }
}