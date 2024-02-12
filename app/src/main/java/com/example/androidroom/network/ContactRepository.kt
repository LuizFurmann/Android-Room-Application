package com.example.androidroom.network

import androidx.lifecycle.LiveData
import com.example.androidroom.data.ContactDao
import com.example.androidroom.model.Contact

class ContactRepository (private val contactDao: ContactDao) {
    fun readAllData() : LiveData<List<Contact>> = contactDao.readALlData()

    suspend fun addTask(task: Contact) {
        contactDao.addTask(task)
    }

    suspend fun updateTask(task: Contact) {
        contactDao.updateTask(task)
    }

    suspend fun deleteTask(task: Contact) {
        contactDao.deleteTask(task)
    }
}