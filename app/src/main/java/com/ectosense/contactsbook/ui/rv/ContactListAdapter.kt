package com.ectosense.contactsbook.ui.rv

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SectionIndexer
import androidx.recyclerview.widget.RecyclerView
import com.ectosense.contactsbook.databinding.ContactListItemBinding
import com.ectosense.contactsbook.db.Person

class ContactListAdapter() : RecyclerView.Adapter<ContactListViewHolder>(), SectionIndexer {
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
        // pupilListViewModel.setSelectedMovie(movieList[position])
        }


    }

    override fun getItemCount(): Int {
        return personList.size
    }

    fun updateContactsList(personList: List<Person>) {
//        if(this.personList.size>personList.size) {l

        this.personList = personList.toMutableList()
        notifyDataSetChanged()
//        }else{
//            this.personList.addAll(this.personList.size, pupilList.subList(this.personList.size, pupilList.size).toMutableList())
//            notifyItemRangeInserted(this.personList.size , pupilList.size)
//        }
    }

    override fun getSections(): Array<String> {
        val sections = ArrayList<String>()
        mSectionPositions = ArrayList()
        var i = 0
        val size: Int = personList.size
        while (i < size) {
            val section: String =
                java.lang.String.valueOf(personList[i].firstName.toCharArray().get(0)).toUpperCase()
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