package com.example.wordlemix.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Player(
    @PrimaryKey(autoGenerate = true)
    val dbId: Long = 0,
    val username: String,
    val record: Int
    ){
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}

fun getPlayers(): List<Player>{
    return listOf(
        Player(username = "Anna", record = 3000),
        Player(username = "Stefan", record = 2000),
        Player(username = "Manu", record = 1000)
    )
}