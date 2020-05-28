package com.ectosense.contactsbook.network

import androidx.lifecycle.LiveData
import com.ectosense.contactsbook.db.AppDatabase
import com.ectosense.contactsbook.db.Person
import com.ectosense.contactsbook.db.PersonList


class ContactRepositoryImpl(val database: AppDatabase, val contactApi: ContactApi) :
    ContactRepository {
    override suspend fun syncContacts(): ArrayList<Person>{
        val apiResponse = contactApi.syncContacts()
        database.contactDao.insertContactList(apiResponse)
        return apiResponse
    }

    override fun getSavedContactList(): LiveData<List<Person>> {
        return database.contactDao.getContactList()
    }

    override suspend fun addNewContact(person: Person): Person? {
        database.contactDao.insertNewContact(person)
        return person
    }

    override suspend fun deleteContact(person: Person) {
//        database.contactDao
    }


    override suspend fun updateContact(person: Person) {
        database.contactDao.updateContact(person)
    }
}