package com.example.androidroom.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.androidroom.model.Contact

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTask(task: Contact)

    @Update
    suspend fun updateTask(task: Contact)

    @Delete
    suspend fun deleteTask(task: Contact)

    @Query("SELECT * FROM contact_table ORDER BY name DESC")
    fun readALlData(): LiveData<List<Contact>>
}