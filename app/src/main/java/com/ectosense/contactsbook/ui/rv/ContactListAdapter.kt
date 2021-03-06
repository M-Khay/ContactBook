package com.ectosense.contactsbook.ui.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SectionIndexer
import androidx.recyclerview.widget.RecyclerView
import com.ectosense.contactsbook.databinding.ContactListItemBinding
import com.ectosense.contactsbook.db.Person
import com.ectosense.contactsbook.ui.ContactViewModel
import kotlinx.android.synthetic.main.contact_list_item.view.*

class ContactListAdapter(var contactViewModel: ContactViewModel, private var recyclerViewClickListener: RecyclerViewClickListener) :
    RecyclerView.Adapter<ContactListViewHolder>(), SectionIndexer {
    private var personList = mutableListOf<Person>()

    private lateinit var mSectionPositions: ArrayList<Int>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
        val contactListItemBinding =
            ContactListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactListViewHolder(contactListItemBinding.root, contactListItemBinding)
    }

    override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
        holder.setData(personList[position])
        // To Implement ClickListener
        holder.itemView.setOnClickListener {
            contactViewModel.selectContact(personList[position])
            recyclerViewClickListener.onRowClicked(position)
        }
        holder.itemView.favourite_Button.setOnClickListener {
            val updatedPerson = personList[position]
            updatedPerson.favorite = !updatedPerson.favorite
            contactViewModel.updateContact(personList[position])

//            recyclerViewClickListener.onFavoriteClicked(position,!updatedPerson.favorite)
        }


    }

    override fun getItemCount(): Int {
        return personList.size
    }

    fun updateContactsList(personList: List<Person>) {
        if (this.personList.size < personList.size) {
            this.personList = personList.toMutableList()
            notifyDataSetChanged()
        }
//        else if (personList.size >= this.personList.size) {
//            this.personList.addAll(this.personList.size, personList.subList(this.personList.size, personList.size).toMutableList())
//            notifyItemRangeInserted(this.personList.size , personList.size)
//        }
    }

    override fun getSections(): Array<String> {
        val sections = ArrayList<String>()
        mSectionPositions = ArrayList()
        var i = 0
        val size: Int = personList.size
        while (i < size) {
            val section: String =
                java.lang.String.valueOf(personList[i].firstName.toUpperCase().toCharArray().get(0)).toUpperCase()
            if (!sections.contains(section)) {
                sections.add(section)
                mSectionPositions.add(i)
            }
            i++
        }

        return sections.toTypedArray()

    }

    override fun getPositionForSection(i: Int): Int {
        return mSectionPositions[i]
    }

    override fun getSectionForPosition(i: Int): Int {
        return 0
    }

}