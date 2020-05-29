package com.ectosense.contactsbook.network

import androidx.lifecycle.LiveData
import com.ectosense.contactsbook.db.Person
import com.ectosense.contactsbook.db.PersonList

interface ContactRepository {
    suspend fun syncContacts(): ArrayList<Person>
    fun getSavedContactList(): LiveData<List<Person>>

    suspend fun updateContact(person: Person)
    suspend fun addOrEditContact(person: Person, isNewContact : Boolean): Person
    suspend fun deleteContact(person: Person)
}
