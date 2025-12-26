package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist

import android.Manifest
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.contactlist.ContactItem
import com.example.androidcourseshpp.data.contactlist.SelectableContactItem
import com.example.androidcourseshpp.databinding.FragmentContactlistBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.adapter.ContactItemDecoration
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.adapter.ContactsAdapter
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.adapter.ContactItemActions
import com.example.androidcourseshpp.ui.screens.main.userinfo.TabSwitchable
import com.example.androidcourseshpp.ui.screens.main.userinfo.UserInfoFragmentDirections
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactListFragment :
    BaseFragment<FragmentContactlistBinding>(FragmentContactlistBinding::inflate) {

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
        viewModel.setEvent(ContactListContract.Event.UpdateContactList)
        createContactListAdapter()
        initRecyclerView()
        initSwipeToDeleteOfContactItem()

        setListeners()
        setObservers()
        setOnBackPressedListener()
    }

    private fun createContactListAdapter() {
        adapter = ContactsAdapter(getItemActions())
    }

    private fun getItemActions(): ContactItemActions = with(binding) {
        return object : ContactItemActions {
            override fun deleteContactItem(contactItem: ContactItem, position: Int) {
                viewModel.setEvent(
                    ContactListContract.Event.ContactItemDeleted(
                        contactItem,
                        position
                    )
                )
                showUndoDeletingSnackBarItem(contactItem, position)
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

    override fun setObservers() {
        collectFlow(viewModel.state) { state ->
            val contactList = state.contactList
            adapter.submitList(contactList.map { contactItem ->
                SelectableContactItem(
                    contactItem,
                    false
                )
            })
            binding.progressBarRequest.isVisible = state.isProgressBarShowed
        }

        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is ContactListContract.Effect.NavigateToUserProfileScreen -> moveBackToUserProfileScreen()
                is ContactListContract.Effect.NavigateToAddContactsScreen -> moveToAddContactsScreen()
                is ContactListContract.Effect.NavigateToDetailsScreen -> moveToDetailsScreen(
                    effect.contact
                )
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
    }


    private fun showUndoDeletingSnackBarItem(contactItem: ContactItem, position: Int) {
        val undoDeletingSnackBar = Snackbar.make(
            binding.root,
            R.string.snackbar_text,
            Snackbar.LENGTH_LONG
        )

        undoDeletingSnackBar.setAction(R.string.snackbar_action_text) {
            viewModel.setEvent(ContactListContract.Event.ContactItemAdded(contactItem, position))
            viewModel.deletedItems.pop()

            if (!viewModel.deletedItems.isEmpty()) {
                val deletedItem = viewModel.deletedItems.peek()
                showUndoDeletingSnackBarItem(deletedItem.first, deletedItem.second)
            }

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
                    showUndoDeletingSnackBarItem(deletedItem, adapterPosition)
                    viewModel.setEvent(
                        ContactListContract.Event.ContactItemDeleted(
                            deletedItem,
                            adapterPosition
                        )
                    )
                }
            })

        helper.attachToRecyclerView(binding.recyclerViewContacts)
    }


    fun moveBackToUserProfileScreen() {
        val parentFragment = parentFragment as? TabSwitchable
        parentFragment?.moveToUserProfileTab()
    }

    private fun moveToDetailsScreen(contactItem: ContactItem) {
        val extras = FragmentNavigatorExtras(sharedContactProfilePhoto to contactItem.id.toString())

        val direction = UserInfoFragmentDirections
            .actionUserInfoFragmentToContactDetailsFragment(contactItem.toContactDetailsEntity())

        findNavController().navigate(direction, extras)
    }

    private fun moveToAddContactsScreen() {
        val direction = UserInfoFragmentDirections.actionUserInfoFragmentToAddContactsFragment()

        findNavController().navigate(direction)
    }

}