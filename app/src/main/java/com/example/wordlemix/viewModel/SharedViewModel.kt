package com.example.wordlemix.viewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordlemix.data.Player
import com.example.wordlemix.data.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        repository.addPlayer(player)
    }

    suspend fun deletePlayer(player: Player){
        repository.deletePlayer(player)
    }

    suspend fun updatePlayer(player: Player){
        repository.updatePlayer(player)
    }

    suspend fun getByUsername(username: String): Player{
        var player : Player = repository.getByUsername(username)
        return player
    }

}