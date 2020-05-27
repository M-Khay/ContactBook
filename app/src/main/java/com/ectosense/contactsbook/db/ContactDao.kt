package com.ectosense.contactsbook.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao  {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContactList(personList : List<Person>)

    @Query("SELECT * FROM Person ORDER BY first_name ASC")
    fun getContactList() : LiveData<List<Person>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewContact(newPerson: Person)

    @Delete
    suspend fun removeContact(person: Person)
}