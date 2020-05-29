package com.ectosense.contactsbook.network

import androidx.lifecycle.LiveData
import com.ectosense.contactsbook.db.AppDatabase
import com.ectosense.contactsbook.db.Person


class ContactRepositoryImpl(val database: AppDatabase, val contactApi: ContactApi) :
    ContactRepository {
    override suspend fun syncContacts(): ArrayList<Person> {
        val apiResponse = contactApi.syncContacts()
        database.contactDao.insertContactList(apiResponse)
        return apiResponse
    }

    override fun getSavedContactList(): LiveData<List<Person>> {
        return database.contactDao.getContactList()
    }

    override suspend fun addOrEditContact(person: Person, isNewContact: Boolean): Person {
        if (isNewContact)
            contactApi.addNewContact("application/json", person)
        database.contactDao.insertNewContact(person)
        return person
    }

    override suspend fun deleteContact(person: Person) {
        database.contactDao.removeContact(person)
    }

    override suspend fun updateContact(person: Person) {
        database.contactDao.updateContact(person)
    }
}