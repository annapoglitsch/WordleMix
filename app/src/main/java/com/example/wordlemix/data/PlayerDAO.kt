package com.example.wordlemix.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDAO {
    @Insert
    suspend fun add(player: Player)

    @Update
    suspend fun update(player: Player)

    @Delete
    suspend fun delete(player: Player)

    @Query("SELECT * from player where dbId=:id")
    fun get(id: Int): Flow<Player>

    @Query("SELECT * from player")
    fun getAll(): Flow<List<Player>>

}