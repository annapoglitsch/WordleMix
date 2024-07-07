package com.example.wordlemix

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wordlemix.data.PlayerRepository
import com.example.wordlemix.viewModel.SharedViewModel

class SharedViewModelFactory(private val repository: PlayerRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if(modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            return SharedViewModel(repository = repository) as T
        }

        throw IllegalArgumentException("Unknown Viewmodel class")

    }
}