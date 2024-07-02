package com.example.wordlemix.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true)
    val dbId: Long = 0,
    val username: String,
    val record: Int,
    )