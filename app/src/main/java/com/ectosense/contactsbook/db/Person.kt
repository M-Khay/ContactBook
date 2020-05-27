package com.ectosense.contactsbook.db

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["phone", "email"])
data class Person(

    @ColumnInfo(name = "first_name")
    @SerializedName("firstname")
    val firstName: String,

    @ColumnInfo(name = "last_name")
    @SerializedName("lastname")
    val lastName: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "favorite")
    val favorite: Boolean
)

class PersonList(
    val personList: List<Person>
)