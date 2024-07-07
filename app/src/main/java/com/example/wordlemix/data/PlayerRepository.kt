package com.example.wordlemix.data

import kotlinx.coroutines.flow.Flow

class PlayerRepository(private val playerDAO: PlayerDAO) {

    suspend fun addPlayer(player: Player) = playerDAO.add(player)

    suspend fun updatePlayer(player: Player) = playerDAO.update(player)

    suspend fun deletePlayer(player: Player) = playerDAO.delete(player)

    fun getAllPlayers(): Flow<List<Player>> = playerDAO.getAll()

    fun getById(id: Int): Flow<Player?> = playerDAO.get(id)

    suspend fun getByUsername(username: String): Player = playerDAO.getByUsername(username)
}