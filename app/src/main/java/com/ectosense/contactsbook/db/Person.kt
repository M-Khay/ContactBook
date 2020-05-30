package com.ectosense.contactsbook.db

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ectosense.contactsbook.R
import com.google.gson.annotations.SerializedName

@Entity(primaryKeys = ["phone", "email"])
data class Person(

    @ColumnInfo(name = "first_name")
    @SerializedName("firstname")
    var firstName: String,

    @ColumnInfo(name = "last_name")
    @SerializedName("lastname")
    val lastName: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean,

    @ColumnInfo(name = "photo")
    val photo: String?
) {

    companion object {
        @JvmStatic
        @BindingAdapter("image")
        fun loadImage(imageView: ImageView, poster: String) {
            Glide.with(imageView.context)
                .setDefaultRequestOptions(
                    RequestOptions()
                        .circleCrop()
                ).load(poster)
                .placeholder(R.mipmap.ic_image_placeholder_round)
                .into(imageView)
        }
    }
}

    class PersonList(
        val personList: List<Person>
    )