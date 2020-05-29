package com.ectosense.contactsbook.di

import android.content.Context
import androidx.room.Room
import com.ectosense.contactsbook.db.AppDatabase
import com.ectosense.contactsbook.network.ContactApi
import com.ectosense.contactsbook.utils.Constant.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ContactAPIFactory {

    fun retrofitPupil(): ContactApi {
        val builder = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        builder.addInterceptor(logging)

        val gson = GsonBuilder()
            .setLenient()
            .create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(builder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(ContactApi::class.java)
    }
}

object DatabaseFactory {

    fun getDBInstance(context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "ContactsDB")
            .fallbackToDestructiveMigration()
            .build()
}