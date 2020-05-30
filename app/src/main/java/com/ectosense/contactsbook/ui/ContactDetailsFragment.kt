package com.ectosense.contactsbook.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ectosense.contactsbook.R
import com.ectosense.contactsbook.databinding.FragmentContactDetailsBinding
import kotlinx.android.synthetic.main.fragment_contact_details.*


class ContactDetailsFragment : Fragment(), View.OnClickListener {

    private lateinit var actionBarListener: ActionBarCallBack
    private lateinit var contactViewModel: ContactViewModel
    private var selectedContact: com.ectosense.contactsbook.db.Person? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        contactViewModel = ViewModelProvider(requireActivity()).get(ContactViewModel::class.java)
        val binding: FragmentContactDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contact_details, container, false)
        binding.lifecycleOwner = this
        selectedContact = contactViewModel.selectedContact.value
        binding.personDetails = selectedContact
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        call.setOnClickListener(this)
        message.setOnClickListener(this)
        email.setOnClickListener(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionBarCallBack) {
            actionBarListener = context
        } else {
            throw ClassCastException("$context must implement ActionBarCallBack")
        }
    }


    override fun onResume() {
        super.onResume()
        actionBarListener.showActionBarWithIcon("Contact's", true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.contact_details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> {
                parentFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        AddOrEditContactFragment.newInstance(false)
                    )
                    .addToBackStack(null)
                    .commit()
                return true
            }
            else -> {
                Log.d(
                    "Main Activity",
                    "No Action taken, please check implementation details of the button."
                )
                return true
            }
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.call -> {
                val callIntent = Intent()
                callIntent.action = Intent.ACTION_DIAL // Action for what intent called for
                callIntent.data = Uri.parse("tel: ${selectedContact!!.phone}") // Data with intent respective action on intent
                startActivity(callIntent)
            }
            R.id.message -> {
                val smsIntent = Intent(Intent.ACTION_VIEW)
                smsIntent.type = "vnd.android-dir/mms-sms"
                smsIntent.putExtra("address", selectedContact?.phone)
                smsIntent.putExtra("sms_body", "")
                smsIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(Intent.createChooser(smsIntent, "Choose Your Messaging App :"))

            }
            R.id.email -> {
                val email = Intent(Intent.ACTION_SEND)
                email.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(selectedContact!!.email))
                email.putExtra(Intent.EXTRA_SUBJECT, "")
                email.putExtra(Intent.EXTRA_TEXT, "")
                email.type = "message/rfc822"
                startActivity(Intent.createChooser(email, "Choose an Email client :"))
            }
        }
    }


}
