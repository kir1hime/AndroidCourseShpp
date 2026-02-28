package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model.ContactItem
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model.SelectableContactItem
import com.example.androidcourseshpp.databinding.FragmentContactlistBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.main.addcontacts.TO_RELOAD_CONTACT_LIST
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.adapter.ContactItemDecoration
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.adapter.ContactsAdapter
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.adapter.ContactItemActions
import com.example.androidcourseshpp.ui.screens.main.userinfo.TabSwitchable
import com.example.androidcourseshpp.ui.screens.main.userinfo.UserInfoFragmentDirections
import com.example.androidcourseshpp.ui.utils.onChangeTextListener
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactListFragment :
    BaseFragment<FragmentContactlistBinding>(FragmentContactlistBinding::inflate), Searchable {

    private val viewModel by viewModels<ContactListViewModel>()

    private lateinit var sharedContactProfilePhoto: ImageView

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveBackToUserProfileScreen()
            }
        }

    private lateinit var adapter: ContactsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reloadContactList()

        createContactListAdapter()
        initRecyclerView()
        initSwipeToDeleteOfContactItem()

        setListeners()
        setObservers()
        setOnBackPressedListener()
    }

    private fun reloadContactList() {
        val savedStateHandle = findNavController().currentBackStackEntry?.savedStateHandle

        val toReloadLiveData =
            savedStateHandle?.getLiveData<Boolean>(TO_RELOAD_CONTACT_LIST)

        toReloadLiveData?.observe(viewLifecycleOwner) {
            viewModel.setEvent(ContactListContract.Event.LoadContactList)
            savedStateHandle.remove<Boolean>(
                TO_RELOAD_CONTACT_LIST
            )

        }
    }

    private fun createContactListAdapter() {
        adapter = ContactsAdapter(getItemActions())
    }

    private fun getItemActions(): ContactItemActions = with(binding) {
        return object : ContactItemActions {
            override fun deleteContactItem(contactItem: ContactItem, position: Int) {
                viewModel.setEvent(
                    ContactListContract.Event.ContactItemDeleted(
                        contactItem
                    )
                )
                showUndoDeletingItemSnackBar(contactItem)
            }

            override fun showContactItemDetails(contactItem: ContactItem, avatar: ImageView) {
                sharedContactProfilePhoto = avatar
                viewModel.setEvent(ContactListContract.Event.OnItemClicked(contactItem))
            }

            override fun showFloatingDeleteButton() {
                floatingButtonDeleteSelectedItems.visibility = View.VISIBLE
            }

            override fun hideFloatingDeleteButton() {
                floatingButtonDeleteSelectedItems.visibility = View.GONE
            }
        }
    }

    private fun setOnBackPressedListener() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun initRecyclerView() = with(binding.recyclerViewContacts) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = this@ContactListFragment.adapter

        addItemDecoration(
            ContactItemDecoration(
                resources.getDimensionPixelSize(R.dimen.contacts_recycle_view_space_size_between_items)
            )
        )
    }

    override fun setObservers() = with(binding) {

        collectFlow(viewModel.state) { state ->
            val contactList = state.contactList

            if (state.isSearchMode) {
                showSearchBar()
                collectFlow(viewModel.filteredContactList) { filteredContactList ->
                    if (filteredContactList.isEmpty() && textInputLayoutSearch.isVisible) {
                        textViewNoResultsFound.isVisible = true
                        textViewAdvice.isVisible = true
                    } else {
                        textViewNoResultsFound.isVisible = false
                        textViewAdvice.isVisible = false
                    }

                    adapter.submitList(filteredContactList.map { contactItem ->
                        SelectableContactItem(
                            contactItem,
                            false
                        )
                    })
                }
            } else {
                adapter.submitList(contactList.map { contactItem ->
                    SelectableContactItem(
                        contactItem,
                        false
                    )
                })
            }

            progressBarRequest.isVisible = state.isProgressBarShowed
            buttonTryAgain.isVisible = state.isTryAgainButtonShowed
            recyclerViewContacts.isVisible = !state.isTryAgainButtonShowed
            textViewNoResultsFound.isVisible = false
            textViewAdvice.isVisible = false
        }

        var toast: Toast? = null
        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is ContactListContract.Effect.HideSearchBar -> hideSearchBar()
                is ContactListContract.Effect.ShowSearchBar -> showSearchBar()
                is ContactListContract.Effect.NavigateToUserProfileScreen -> moveBackToUserProfileScreen()
                is ContactListContract.Effect.NavigateToAddContactsScreen -> moveToAddContactsScreen()
                is ContactListContract.Effect.NavigateToDetailsScreen -> moveToDetailsScreen(
                    effect.contact
                )

                is ContactListContract.Effect.ShowUndoDeletingItemSnackBar -> showUndoDeletingItemSnackBar(
                    effect.deletedItem
                )

                is ContactListContract.Effect.ShowToast -> {
                    toast?.cancel()
                    toast = makeToast(effect.toastMessageResId)
                    toast.show()
                }
            }
        }
    }

    override fun setListeners() = with(binding) {
        imageButtonArrowBack.setOnClickListener {
            viewModel.setEvent(ContactListContract.Event.OnArrowBackButtonClicked)
        }
        textViewAddContacts.setOnClickListener {
            viewModel.setEvent(ContactListContract.Event.OnAddContactClicked)
        }
        floatingButtonDeleteSelectedItems.setOnClickListener {
            viewModel.setEvent(
                ContactListContract.Event.OnDeleteSelectedItemsFloatingButtonClicked(
                    adapter.selectedItems
                )
            )
            floatingButtonDeleteSelectedItems.visibility = View.GONE
        }

        buttonTryAgain.setOnClickListener {
            viewModel.setEvent(ContactListContract.Event.LoadContactList)
        }

        imageButtonSearch.setOnClickListener {
            viewModel.setEvent(ContactListContract.Event.SearchModeSwitched(true))
            viewModel.setEvent(ContactListContract.Event.OnSearchButtonClicked)
        }
        imageButtonHideSearch.setOnClickListener {
            viewModel.setEvent(ContactListContract.Event.SearchModeSwitched(false))
            viewModel.setEvent(ContactListContract.Event.OnHideSearchButtonCLicked)
        }

        editTextSearch.onChangeTextListener { sequence, _, _, _ ->
            viewModel.setEvent(ContactListContract.Event.OnSearchBarTextChanged(sequence.toString()))
        }
    }

    override fun hideSearchBar(): Unit = with(binding) {
        editTextSearch.setText("")
        textInputLayoutSearch.isVisible = false
        imageButtonSearch.isVisible = true
        imageButtonHideSearch.isVisible = false
        recyclerViewContacts.post {
            recyclerViewContacts.scrollToPosition(0)
        }
    }

    private fun showSearchBar() = with(binding) {
        textInputLayoutSearch.isVisible = true
        imageButtonSearch.isVisible = false
        imageButtonHideSearch.isVisible = true
    }


    private fun showUndoDeletingItemSnackBar(contactItem: ContactItem) {
        val undoDeletingSnackBar = Snackbar.make(
            binding.root,
            R.string.snackbar_text,
            Snackbar.LENGTH_LONG
        )

        undoDeletingSnackBar.setAction(R.string.snackbar_action_text) {
            viewModel.setEvent(ContactListContract.Event.ContactItemAdded(contactItem))

        }.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.custom_primary_color))

        undoDeletingSnackBar.show()
    }

    private fun initSwipeToDeleteOfContactItem() {
        val helper =
            ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val adapterPosition = viewHolder.adapterPosition

                    val deletedItem = viewModel.state.value.contactList[adapterPosition]
                    showUndoDeletingItemSnackBar(deletedItem)
                    viewModel.setEvent(
                        ContactListContract.Event.ContactItemDeleted(
                            deletedItem
                        )
                    )
                }
            })

        helper.attachToRecyclerView(binding.recyclerViewContacts)
    }


    private fun moveBackToUserProfileScreen() {
        val parentFragment = parentFragment as? TabSwitchable
        parentFragment?.moveToUserProfileTab()
    }

    private fun moveToDetailsScreen(contactItem: ContactItem) {
        val extras = FragmentNavigatorExtras(sharedContactProfilePhoto to contactItem.id.toString())

        val direction = UserInfoFragmentDirections
            .actionUserInfoFragmentToContactDetailsFragment(contactItem.toContactDetails())

        findNavController().navigate(direction, extras)
    }

    private fun moveToAddContactsScreen() {
        val direction = UserInfoFragmentDirections.actionUserInfoFragmentToAddContactsFragment()

        findNavController().navigate(direction)
    }
}