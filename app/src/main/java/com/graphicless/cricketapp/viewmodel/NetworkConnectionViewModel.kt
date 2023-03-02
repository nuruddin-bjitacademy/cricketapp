package com.graphicless.cricketapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NetworkConnectionViewModel: ViewModel()  {
    // Check internet
    private val _isNetworkAvailable = MutableLiveData<Boolean>()
    val isNetworkAvailable: LiveData<Boolean> get() = _isNetworkAvailable

    fun networkAvailable() {
        _isNetworkAvailable.postValue(true)
    }

    fun networkLost() {
        _isNetworkAvailable.postValue(false)
    }
    // End check internet
}