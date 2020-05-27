package com.ectosense.contactsbook.network

import androidx.lifecycle.LiveData
import com.ectosense.contactsbook.db.AppDatabase
import com.ectosense.contactsbook.db.Person
import com.ectosense.contactsbook.db.PersonList


class ContactRepositoryImpl(val database: AppDatabase, val contactApi: ContactApi) :
    ContactRepository {
    override suspend fun syncContacts(page: Int): PersonList {
        TODO("Not yet implemented")
    }

    override fun getSavedContactList(): LiveData<List<Person>> {
        TODO("Not yet implemented")
    }

    override suspend fun addNewContact(person: Person): Person? {
        TODO("Not yet implemented")
    }

    override suspend fun deleteContact(person: Person) {
        TODO("Not yet implemented")
    }


}