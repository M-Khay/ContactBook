package com.ectosense.contactsbook.network

import androidx.lifecycle.LiveData
import com.ectosense.contactsbook.db.Person
import com.ectosense.contactsbook.db.PersonList

interface ContactRepository {
    suspend fun syncContacts(page: Int): PersonList
    fun getSavedContactList(): LiveData<List<Person>>

    suspend fun addNewContact(person: Person): Person?
    suspend fun deleteContact(person: Person)
}
