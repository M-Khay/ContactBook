package com.ectosense.contactsbook.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ectosense.contactsbook.R


class AddOrEditContactFragment : Fragment() {

    private lateinit var viewModel: ContactViewModel
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
        return inflater.inflate(R.layout.add_or_edit_contact_fragment, container, false)
    }
    override fun onResume() {
        super.onResume()
        actionBarListener.showActionBarWithIcon("", true)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
