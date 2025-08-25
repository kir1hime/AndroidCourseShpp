package com.example.androidcourseshpp.ui.screens.userinfo.contacts

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.contactlistdata.ContactItem
import com.example.androidcourseshpp.databinding.FragmentContactlistBinding
import com.example.androidcourseshpp.ui.utils.navigator
import com.example.androidcourseshpp.ui.screens.userinfo.contacts.AddContactDialog.Companion.CAREER_KEY
import com.example.androidcourseshpp.ui.screens.userinfo.contacts.AddContactDialog.Companion.NAME_KEY
import com.example.androidcourseshpp.ui.screens.userinfo.contacts.AddContactDialog.Companion.RESPONSE_KEY
import com.example.androidcourseshpp.ui.screens.userinfo.contacts.adapters.ContactItemDecoration
import com.example.androidcourseshpp.ui.screens.userinfo.contacts.adapters.ContactsAdapter
import com.example.androidcourseshpp.ui.screens.userinfo.contacts.adapters.ItemActions
import com.example.androidcourseshpp.data.Tab
import com.example.androidcourseshpp.ui.screens.TabSwitchable
import com.example.androidcourseshpp.ui.utils.factory
import com.google.android.material.snackbar.Snackbar

class ContactListFragment : Fragment() {

    private lateinit var binding: FragmentContactlistBinding
    private val viewModel by viewModels<ContactListViewModel> { factory() }
    private lateinit var requestPermissionsLauncher: ActivityResultLauncher<String>

    private val onBackPressedCallback: OnBackPressedCallback =
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
              moveToMyProfileScreen()
            }
        }

    private val adapter by lazy {
        ContactsAdapter(getItemActions())
    }

    private companion object {
        const val NEW_CONTACT_AVATAR =
            "https://kartinki.pics/uploads/posts/2022-02/1645235615_4-kartinkin-net-p-kroliki-kartinki-4.jpg"
    }

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

        initRecyclerView()
        initSwipeToDeleteOfContactItem()

        setListeners()
        setObservers()
        setAddContactDialogListener()
        setOnBackPressedListener()
    }

    private fun getItemActions(): ItemActions {
        return object : ItemActions {
            override fun deleteContactItem(contactItem: ContactItem, position: Int) {
                viewModel.deleteContactItem(contactItem)
                showUndoDeletingSnackBarItem(contactItem, position)
            }

            override fun showContactItemDetails(contactItem: ContactItem, avatar: ImageView) {
                navigator().moveToDetailsScreen(contactItem, avatar)
            }
        }
    }

    private fun setOnBackPressedListener() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            onBackPressedCallback
        )
    }

    private fun initRecyclerView() = with(binding.rvContacts) {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = this@ContactListFragment.adapter

        addItemDecoration(
            ContactItemDecoration(
                resources.getDimensionPixelSize(R.dimen.contacts_recycle_view_space_size_between_items)
            )
        )
    }

    private fun setObservers() {
        viewModel.contactList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setListeners() = with(binding) {
        ibtArrowBack.setOnClickListener {
            moveToMyProfileScreen()
        }
        tvAddContacts.setOnClickListener {
            showAddContactDialog()
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
            viewModel.addContactItem(contactItem, position)
        }.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.custom_primary_color))

        undoDeletingSnackBar.show()
    }

    private fun setAddContactDialogListener() {
        childFragmentManager.setFragmentResultListener(
            AddContactDialog.REQUEST_KEY, this
        ) { _, data ->

            val which = data.getInt(RESPONSE_KEY)

            when (which) {
                AlertDialog.BUTTON_POSITIVE -> {
                    val newContact = createNewContact(data)
                    if (!viewModel.isNewContactDataIsBlank(newContact)) {
                        viewModel.addContactItem(newContact, viewModel.contactList.value?.size ?: 0)
                    }
                }
            }
        }
    }

    private fun createNewContact(data: Bundle): ContactItem {
        val newContactName = data.getString(NAME_KEY)
        val newContactCareer = data.getString(CAREER_KEY)

        val contactList = viewModel.contactList.value
        val lastId: Int = contactList?.get(contactList.size - 1)?.id ?: 0

        val newContact = ContactItem(
            lastId + 1,
            newContactName ?: "",
            newContactCareer ?: "",
            NEW_CONTACT_AVATAR
        )

        return newContact
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
                    with(viewHolder) {
                        val deletedItem = viewModel.contactList.value!![adapterPosition]
                        showUndoDeletingSnackBarItem(deletedItem, adapterPosition)
                    }
                    viewModel.deleteContactItem(viewHolder.adapterPosition)
                }
            })

        helper.attachToRecyclerView(binding.rvContacts)
    }

    private fun checkPermissions() {
        requestPermissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionsGranted ->
                if (isPermissionsGranted) {
                    viewModel.updateContactList()
                }
            }
    }

    private fun  moveToMyProfileScreen(){
        val parentFragment = parentFragment as? TabSwitchable
        parentFragment?.moveToMyProfileTab()
    }

}