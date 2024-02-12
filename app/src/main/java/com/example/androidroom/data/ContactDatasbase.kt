package com.example.agenda.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidroom.data.ContactDao
import com.example.androidroom.model.Contact

@Database(
    entities = [Contact::class],
    version = 1,
    exportSchema = true
)
abstract class ContactDatasbase : RoomDatabase(){

    abstract fun contactDao(): ContactDao

    companion object{
        @Volatile
        private var INSTANCE: ContactDatasbase? = null

        fun getDatabase(context: Context): ContactDatasbase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatasbase::class.java,
                    "contact_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}