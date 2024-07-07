package com.example.wordlemix.viewModel

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordlemix.PlayerPreferences
import com.example.wordlemix.data.Player
import com.example.wordlemix.data.PlayerRepository
import com.example.wordlemix.game.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SharedViewModel(private val repository: PlayerRepository) : ViewModel() {
    private val _players = MutableStateFlow(listOf<Player>())
    val players: StateFlow<List<Player>> = _players.asStateFlow()

    private val _showPopUp = MutableStateFlow(true)
    val showPopUp: StateFlow<Boolean> = _showPopUp



    init{
        viewModelScope.launch {
            repository.getAllPlayers().collect(){playersList ->
                _players.value = playersList
            }
        }
    }


    suspend fun toggleDoneState(player: Player){
        viewModelScope.launch {
            repository.updatePlayer(player)
        }
    }
    fun popUpShown() {
        viewModelScope.launch {
            _showPopUp.value = false
        }
    }

    private val _isDarkBool = MutableStateFlow(false)
    val isDarkBool: StateFlow<Boolean>
        get() = _isDarkBool

    fun setBoolean(value: Boolean){
        _isDarkBool.value = value
    }

    suspend fun addPlayer(player: Player){
        viewModelScope.launch {
            repository.addPlayer(player)
        }
    }

    suspend fun deletePlayer(player: Player){
        viewModelScope.launch {
            repository.deletePlayer(player)
        }
    }

    suspend fun updatePlayer(player: Player){
        viewModelScope.launch {
            repository.updatePlayer(player)
        }
    }

    suspend fun getByUsername(username: String): Player{
        var player : Player
        player = Player(username="error", record = 1)
        viewModelScope.launch {
            player = repository.getByUsername(username)
        }
        return player
    }

    suspend fun getPlayerById(id: Int){
        viewModelScope.launch {
            repository.getById(id)
        }
    }
}