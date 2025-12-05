package com.example.androidcourseshpp.ui.screens.userinfo.contactlist

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
import com.example.androidcourseshpp.ui.screens.userinfo.contactlist.addcontactdialog.AddContactDialog.Companion.CAREER_KEY
import com.example.androidcourseshpp.ui.screens.userinfo.contactlist.addcontactdialog.AddContactDialog.Companion.NAME_KEY
import com.example.androidcourseshpp.ui.screens.userinfo.contactlist.addcontactdialog.AddContactDialog.Companion.RESPONSE_KEY
import com.example.androidcourseshpp.ui.screens.userinfo.contactlist.adapter.ContactItemDecoration
import com.example.androidcourseshpp.ui.screens.userinfo.contactlist.adapter.ContactsAdapter
import com.example.androidcourseshpp.ui.screens.userinfo.contactlist.adapter.ItemActions
import com.example.androidcourseshpp.ui.screens.userinfo.TabSwitchable
import com.example.androidcourseshpp.ui.screens.userinfo.UserInfoFragmentDirections
import com.example.androidcourseshpp.ui.screens.userinfo.contactlist.addcontactdialog.AddContactDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContactListFragment : BaseFragment() {

    private lateinit var binding: FragmentContactlistBinding
    private val viewModel by viewModels<ContactListViewModel>()
    private lateinit var requestPermissionsLauncher: ActivityResultLauncher<String>

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveBackToMyProfileScreen()
            }
        }

    private lateinit var adapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactlistBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()
        requestPermissionsLauncher.launch(Manifest.permission.READ_CONTACTS)

        createContactListAdapter()
        initRecyclerView()
        initSwipeToDeleteOfContactItem()

        setListeners()
        setObservers()
        setAddContactDialogListener()
        setOnBackPressedListener()
    }

    private fun createContactListAdapter() {
        adapter = ContactsAdapter(getItemActions())
    }

    private fun getItemActions(): ItemActions = with(binding) {
        return object : ItemActions {
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
                viewModel.setEvent(ContactListContract.Event.OnItemClicked(contactItem, avatar))
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

    private fun setObservers() {
        collectFlow(viewModel.state) { state ->
            val contactList = state.contactList
            adapter.submitList(contactList.map { contactItem ->
                SelectableContactItem(
                    contactItem,
                    false
                )
            })
        }

        collectFlow(viewModel.effect) { effect ->
            when (effect) {
                is ContactListContract.Effect.NavigateToDetailsScreen -> moveToDetailsScreen(
                    effect.contact,
                    effect.avatar
                )

                is ContactListContract.Effect.NavigateToMyProfileScreen -> moveBackToMyProfileScreen()
                is ContactListContract.Effect.ShowAddContactDialog -> showAddContactDialog()
            }
        }
    }

    private fun setListeners() = with(binding) {
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

    private fun showAddContactDialog() {
        AddContactDialog().show(childFragmentManager, AddContactDialog.TAG)
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

    private fun setAddContactDialogListener() {
        childFragmentManager.setFragmentResultListener(
            AddContactDialog.REQUEST_KEY, viewLifecycleOwner
        ) { _, data ->

            val event = data.getInt(RESPONSE_KEY)
            val newContactName = data.getString(NAME_KEY) ?: ""
            val newContactCareer = data.getString(CAREER_KEY) ?: ""

            when (event) {
                AlertDialog.BUTTON_POSITIVE -> viewModel.setEvent(
                    ContactListContract.Event.AddContactDialogEventProcessed(
                        newContactName, newContactCareer
                    )
                )
            }
        }
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

    private fun checkPermissions() {
        requestPermissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionsGranted ->
                if (isPermissionsGranted) {
                    viewModel.setEvent(ContactListContract.Event.PhoneContactsAdded)
                }
            }
    }

    fun moveBackToMyProfileScreen() {
        val parentFragment = parentFragment as? TabSwitchable
        parentFragment?.moveToMyProfileTab()
    }

    private fun moveToDetailsScreen(contact: ContactItem, avatar: ImageView) {
        val extras = FragmentNavigatorExtras(avatar to contact.id.toString())

        val direction =
            UserInfoFragmentDirections.actionUserInfoFragmentToContactDetailsFragment(contact)

        findNavController().navigate(direction, extras)
    }

}