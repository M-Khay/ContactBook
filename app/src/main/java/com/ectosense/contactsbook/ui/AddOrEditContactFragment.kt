package com.ectosense.contactsbook.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ectosense.contactsbook.R
import com.ectosense.contactsbook.db.Person
import kotlinx.android.synthetic.main.add_or_edit_contact_fragment.*


class AddOrEditContactFragment : Fragment() {

    private lateinit var contactViewModel: ContactViewModel
    private lateinit var actionBarListener: ActionBarCallBack

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionBarCallBack) {
            actionBarListener = context
        } else {
            throw ClassCastException("$context must implement ActionBarCallBack")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        contactViewModel = ViewModelProvider(requireActivity()).get(ContactViewModel::class.java)

        return inflater.inflate(R.layout.add_or_edit_contact_fragment, container, false)
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

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.save -> {
            
            val firstName = first_name_et.text.toString()
            val lastName = lastname_et.text.toString()
            val phone = phone_number_et.text.toString()
            val email = email_id_et.text.toString()

            if (validateData(firstName, lastName, phone, "")) {
                contactViewModel.addContact(
                    person = Person(
                        firstName,
                        lastName,
                        email,
                        phone,
                        false,
                        null
                    )
                )
            }
            true
        }
        else -> {
            false
        }
    }


    private fun validateData(
        firstName: String,
        lastName: String,
        phone: String,
        email: String
    ): Boolean {

        if (firstName.isEmpty() && lastName.isEmpty()) {
            Toast.makeText(activity,"Please enter first or last name before saving your contact.", Toast.LENGTH_LONG).show()
            return false
        }
        if (phone.isEmpty() && email.isEmpty()) {
            Toast.makeText(activity,"Please enter phone or email address before saving your contact.", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
