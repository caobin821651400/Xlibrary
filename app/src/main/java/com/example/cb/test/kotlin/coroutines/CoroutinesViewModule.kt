package com.example.cb.test.kotlin.coroutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CoroutinesViewModule : ViewModel() {

    /**
     *
     */
    private fun getData() {
        viewModelScope.launch(Dispatchers.Default) {

        }
    }
}