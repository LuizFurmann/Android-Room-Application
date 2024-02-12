package com.example.androidroom.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.agenda.data.ContactDatasbase
import com.example.androidroom.model.Contact
import com.example.androidroom.network.ContactRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val contactRepository : ContactRepository

    init {
        val contactDao = ContactDatasbase.getDatabase(application).contactDao()
        contactRepository = ContactRepository(contactDao)
    }

    fun readAllData(): LiveData<List<Contact>> = contactRepository.readAllData()

    fun addTask(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.addTask(contact)
        }
    }
    fun updateTask(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.updateTask(contact)
        }
    }

    fun deleteTask(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            contactRepository.deleteTask(contact)
        }
    }
}