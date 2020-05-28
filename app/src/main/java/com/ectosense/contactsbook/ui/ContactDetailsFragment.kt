package com.ectosense.contactsbook.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.ectosense.contactsbook.R

class ContactDetailsFragment : Fragment() {

    private lateinit var actionBarListener: ActionBarCallBack

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_contact_details, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ActionBarCallBack) {
            actionBarListener = context
        } else {
            throw ClassCastException("$context must implement ActionBarCallBack")
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}
