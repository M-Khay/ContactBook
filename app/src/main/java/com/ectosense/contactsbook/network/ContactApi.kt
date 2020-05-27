package com.ectosense.contactsbook.network

import com.ectosense.contactsbook.db.Person
import com.ectosense.contactsbook.db.PersonList
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ContactApi {

    @GET("contacts")
    suspend fun syncContacts(): ArrayList<Person>

    // To be modified.
    @POST("contacts")
    suspend fun addNewContact(
        @Header("Content-Type") contentType: String,
        @Body person: Person
    )

}