package com.example.androidcourseshpp.ui.screens.contacts

import android.Manifest
import android.app.ActivityOptions
import android.app.AlertDialog
import android.content.Intent
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
import com.example.androidcourseshpp.data.contactlistdata.ContactItem
import com.example.androidcourseshpp.databinding.FragmentContactlistBinding
import com.example.androidcourseshpp.ui.BaseFragment
import com.example.androidcourseshpp.ui.screens.contacts.AddContactDialog.Companion.CAREER_KEY
import com.example.androidcourseshpp.ui.screens.contacts.AddContactDialog.Companion.NAME_KEY
import com.example.androidcourseshpp.ui.screens.contacts.AddContactDialog.Companion.RESPONSE_KEY
import com.example.androidcourseshpp.ui.screens.contacts.adapters.ContactItemDecoration
import com.example.androidcourseshpp.ui.screens.contacts.adapters.ContactsAdapter
import com.example.androidcourseshpp.ui.screens.contacts.adapters.ItemActions
import com.example.androidcourseshpp.ui.screens.main.MainActivity
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
              moveToMyProfileScreen()
            }
        }

    private val adapter by lazy {
        ContactsAdapter(getItemActions())
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
                viewModel.deleteContactItem(contactItem, position)
                showUndoDeletingSnackBarItem(contactItem, position)
            }

            override fun showContactItemDetails(contactItem: ContactItem, avatar: ImageView) {
                moveToDetailsScreen(contactItem, avatar)
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
        collectFlow(viewModel.contactList) { adapter.submitList(it) }
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
            AddContactDialog.REQUEST_KEY, this
        ) { _, data ->

            val event = data.getInt(RESPONSE_KEY)
            val newContactName = data.getString(NAME_KEY)
            val newContactCareer = data.getString(CAREER_KEY)

            val newContact = viewModel.createNewContact(newContactName, newContactCareer)

            when (event) {
                AlertDialog.BUTTON_POSITIVE -> viewModel.processAddContactDialogEvent(newContact)
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

                    val deletedItem = viewModel.contactList.value[adapterPosition]
                    showUndoDeletingSnackBarItem(deletedItem, adapterPosition)
                    viewModel.deleteContactItem(deletedItem, adapterPosition)
                }
            })

        helper.attachToRecyclerView(binding.rvContacts)
    }

    private fun checkPermissions() {
        requestPermissionsLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionsGranted ->
                if (isPermissionsGranted) {
                    viewModel.addPhoneContacts()
                }
            }
    }

     private fun moveToMyProfileScreen() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        val options = ActivityOptions.makeCustomAnimation(
            requireContext(),
            R.anim.slide_in_from_left_to_right,
            R.anim.slide_out_from_left_to_right
        )
        startActivity(intent, options.toBundle())
        requireActivity().finish()
    }

     private fun moveToDetailsScreen(contact: ContactItem, avatar: ImageView) {
        val extras = FragmentNavigatorExtras(avatar to contact.id.toString())

        val direction =
            ContactListFragmentDirections.actionContactListFragmentToContactDetailsFragment(contact)

        findNavController().navigate(direction, extras)
    }

}