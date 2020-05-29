package com.ectosense.contactsbook.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ectosense.contactsbook.R
import com.ectosense.contactsbook.databinding.FragmentContactDetailsBinding

class ContactDetailsFragment : Fragment() {

    private lateinit var actionBarListener: ActionBarCallBack
    private lateinit var contactViewModel: ContactViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        contactViewModel = ViewModelProvider(requireActivity()).get(ContactViewModel::class.java)
        val binding: FragmentContactDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_contact_details, container, false)
        binding.lifecycleOwner = this
        binding.personDetails = contactViewModel.selectedContact.value
        return binding.root
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


}
