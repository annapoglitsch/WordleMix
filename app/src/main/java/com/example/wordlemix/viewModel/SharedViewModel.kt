package com.example.wordlemix.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SharedViewModel : ViewModel() {
    private val _sharedThemeBool = MutableStateFlow(false)
    val sharedThemeBool: StateFlow<Boolean>
        get() = _sharedThemeBool

    fun setBoolean(value: Boolean){
        _sharedThemeBool.value = value
    }
}
//ToDo: implement viewmodel for other screens