package com.ectosense.contactsbook.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ectosense.contactsbook.R
import com.ectosense.contactsbook.db.Person
import kotlinx.android.synthetic.main.add_or_edit_contact_fragment.*
import kotlinx.android.synthetic.main.add_or_edit_contact_fragment.view.*


class AddOrEditContactFragment : Fragment() {


    private lateinit var contactViewModel: ContactViewModel
    private lateinit var actionBarListener: ActionBarCallBack
    private var isNewContact: Boolean = true

    companion object {
        const val IS_NEW_CONTACT = "isNewContact"

        @JvmStatic
        fun newInstance(isNewContact: Boolean) = AddOrEditContactFragment().apply {
            arguments = Bundle().apply {
                putBoolean(IS_NEW_CONTACT, isNewContact)
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionBarCallBack && context is ContactListFragment.ChangeFragmentListener) {
            actionBarListener = context
        } else {
            throw ClassCastException("$context must implement ActionBarCallBack")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.add_or_edit_contact_fragment, container, false)
        setHasOptionsMenu(true)
        contactViewModel = ViewModelProvider(requireActivity()).get(ContactViewModel::class.java)
        contactViewModel.addNewContactState.observe(this.viewLifecycleOwner, addNewContactObserver)

        arguments?.getBoolean(IS_NEW_CONTACT)?.let {
            isNewContact = it
        }
        if (!isNewContact) {
            val person = contactViewModel.selectedContact.value
            person?.let {
                view.first_name_et.setText(it.firstName)
                view.lastname_et.setText(it.lastName)
                view.phone_number_et.setText(it.phone)
                view.email_id_et.setText(it.email)
            }
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        actionBarListener.showActionBarWithIcon("", true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_edit_contact_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.d -> {
                val firstName = first_name_et.text.toString()
                val lastName = lastname_et.text.toString()
                val phone = phone_number_et.text.toString()
                val email = email_id_et.text.toString()

                if (validateData(firstName, lastName, phone, "")) {
                    saving_content.visibility = View.VISIBLE
                    val result = contactViewModel.addOrEditContact(
                        person = Person(
                            firstName,
                            lastName,
                            email,
                            phone,
                            false,
                            null
                        ), isNewContact = isNewContact
                    )
                    contactViewModel.selectedContact.value = result
                }
                return true
            }

        }
        return super.onOptionsItemSelected(item)

    }

    private fun validateData(
        firstName: String,
        lastName: String,
        phone: String,
        email: String
    ): Boolean {
        if (firstName.isEmpty() && lastName.isEmpty()) {
            Toast.makeText(
                activity,
                "Please enter first or last name before saving your contact.",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        if (phone.isEmpty() && email.isEmpty()) {
            Toast.makeText(
                activity,
                "Please enter phone or email address before saving your contact.",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        return true
    }

    private val addNewContactObserver = Observer<Boolean> {
        if (it) {
            activity?.onBackPressed()
        } else {
            Toast.makeText(
                activity,
                "Sorry!! Something went wrong, please try again",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
