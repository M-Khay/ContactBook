package com.ectosense.contactsbook.ui.rv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ectosense.contactsbook.databinding.ContactListItemBinding
import com.ectosense.contactsbook.db.Person

class ContactListViewHolder constructor(contactListItemView: View, private val contactListItemBinding: ContactListItemBinding) :
        RecyclerView.ViewHolder(contactListItemView) {

    fun setData(person: Person) {
        contactListItemBinding.personDetails = person
    }

}