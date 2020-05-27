package com.ectosense.contactsbook.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ectosense.contactsbook.R
import com.ectosense.contactsbook.db.Person
import com.ectosense.contactsbook.network.ApiResult
import com.ectosense.contactsbook.network.Error
import com.ectosense.contactsbook.network.Loading
import com.ectosense.contactsbook.network.Success
import com.ectosense.contactsbook.ui.rv.ContactListAdapter
import kotlinx.android.synthetic.main.fragment_contact_list.*
import org.koin.android.viewmodel.ext.android.viewModel

class ContactListFragment : Fragment() {

    interface ChangeFragmentListener {
        fun changeFragment()
    }

    private lateinit var changeFragmentListener: ChangeFragmentListener
    private val contactViewModel by viewModel<ContactViewModel>()
    private lateinit var adapter: ContactListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ChangeFragmentListener) {
            changeFragmentListener = context
        } else {
            throw ClassCastException("The containing activity need to Implement ChangeFragmentListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_list, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        add_new_contact.setOnClickListener {
            changeFragmentListener.changeFragment()
        }
        adapter = ContactListAdapter()

        contact_list.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = this@ContactListFragment.adapter
        }

        contact_list.addItemDecoration(
            DividerItemDecoration(
                contact_list.context,
                DividerItemDecoration.VERTICAL
            )
        )
        contactViewModel.syncContactList()
        contactViewModel._ContactsSyncState.observe(this.viewLifecycleOwner, apiResultObserver)
        contactViewModel.contactList.observe(this.viewLifecycleOwner, dbContactListObeserver)

    }

    private val apiResultObserver = Observer<ApiResult<List<Person>>> { state ->
        when (state) {
            is Success<List<Person>> -> {
                loading_content.visibility = View.GONE
            }
            is Loading -> {
                loading_content.visibility = View.VISIBLE
            }
            is Error -> {
                loading_content.visibility = View.GONE
            }
        }

    }
    private val dbContactListObeserver = Observer<List<Person>> { pupilList ->
        // update adapter.
        adapter.updateContactsList(pupilList)
    }

}
