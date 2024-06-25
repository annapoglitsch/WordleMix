package com.example.wordlemix.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SharedViewModel : ViewModel() {
    private val _isDarkBool = MutableStateFlow(false)
    val isDarkBool: StateFlow<Boolean>
        get() = _isDarkBool

    fun setBoolean(value: Boolean){
        _isDarkBool.value = value
    }
}
//ToDo: change startscreen default colors -> should change when darkmode