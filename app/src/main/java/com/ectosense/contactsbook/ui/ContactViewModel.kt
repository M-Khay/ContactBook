package com.ectosense.contactsbook.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ectosense.contactsbook.db.Person
import com.ectosense.contactsbook.network.ApiResult
import com.ectosense.contactsbook.network.ContactRepository
import com.ectosense.contactsbook.network.Loading
import com.ectosense.contactsbook.network.Success
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactViewModel(private val repository: ContactRepository) : ViewModel() {
    private val TAG = ContactViewModel::class.java.name

    val addNewContactState = MutableLiveData<Boolean>()

    val _ContactsSyncState = MutableLiveData<ApiResult<List<Person>>>()

    val selectedContact = MutableLiveData<Person>()

    val contactList: LiveData<List<Person>>
        get() = repository.getSavedContactList()


    fun syncContactList() {
        if (contactList.value?.size == 0)
            _ContactsSyncState.value =
                Loading(true)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repository.syncContacts()
                withContext(Dispatchers.Main) {
                    _ContactsSyncState.value =
                        Success(result, false)
                }
            } catch (exception: Exception) {
                Log.d(TAG, "Error from API ${exception.localizedMessage}")
                withContext(Dispatchers.Main) {
                    _ContactsSyncState.value =
                        com.ectosense.contactsbook.network.Error(exception, false)
                }
            }
        }
    }

    fun addOrEditContact(person: Person, isNewContact: Boolean): Person {
        var updatedPerson = person
        viewModelScope.launch(Dispatchers.IO) {
            try {
                updatedPerson = repository.addOrEditContact(person,isNewContact)
                withContext(Dispatchers.Main) {
                    addNewContactState.value = true
                }
            } catch (exception: Exception) {
                withContext(Dispatchers.Main) {

                    // For Now we are not handling errors from server side.
                    Log.d(TAG, "Error from API ${exception.localizedMessage}")
                    addNewContactState.value = true
                }
            }
        }
        return updatedPerson
    }

    fun selectContact(person: Person) {
        selectedContact.value = person
    }

    fun updateContact(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.updateContact(person)
            } catch (exception: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d(TAG, "Error while updating Contact ${exception.localizedMessage}")
                }
            }
        }
    }


}
