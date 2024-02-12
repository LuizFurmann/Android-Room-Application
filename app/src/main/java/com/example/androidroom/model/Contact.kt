package com.example.androidroom.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey(autoGenerate = true)
    var taskId : Int,
    var name : String,
    var phone : String
): Serializable
