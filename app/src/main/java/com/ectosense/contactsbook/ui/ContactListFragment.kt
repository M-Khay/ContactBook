package com.ectosense.contactsbook.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ectosense.contactsbook.ui.rv.ContactListAdapter
import com.ectosense.contactsbook.R
import com.ectosense.contactsbook.db.Person
import kotlinx.android.synthetic.main.fragment_contact_list.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ContactListFragment : Fragment() {

    interface ChangeFragmentListener {
        fun changeFragment()
    }

    private lateinit var changeFragmentListener: ChangeFragmentListener
    private val pupilViewModel by viewModel<ContactViewModel>()

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
//        pupilViewModel.syncContacts()
//        pupilViewModel.contactsSyncState.observe(this.viewLifecycleOwner, apiResultObserver)
//        pupilViewModel.personList.observe(this.viewLifecycleOwner, dbPersonListObserver)

        val adapter = ContactListAdapter()
        contact_list.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = adapter
        }

        contact_list.addItemDecoration(
            DividerItemDecoration(
                contact_list.context,
                DividerItemDecoration.VERTICAL
            )
        )

        val temporaryPersonList = buildPersonList()
        adapter.updateTeamList(temporaryPersonList)
    }

    fun buildPersonList(): List<Person>{
        val alphabets = arrayOf("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","z","y","t")
        val result= mutableListOf<Person>()
        for(i in 0..19){
            result.add(Person(alphabets[i],alphabets[i],alphabets[i],i.toString(),false))
        }
        return result
    }

}
