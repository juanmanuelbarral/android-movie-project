package com.example.moviedatabaseapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.facebook.CallbackManager

class LoginViewModel : ViewModel() {

    val callbackManager: CallbackManager = CallbackManager.Factory.create()

    private val _accessToken = MutableLiveData<AccessToken>()
    val accessToken: LiveData<AccessToken>
        get() = _accessToken

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn: LiveData<Boolean>
        get() = _isLoggedIn

    init {
        checkFacebookLoginStatus()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("LoginViewModel", "onCleared called")
    }

    fun checkFacebookLoginStatus() {
        _accessToken.value = AccessToken.getCurrentAccessToken()
        _isLoggedIn.value = _accessToken.value != null && !_accessToken.value!!.isExpired
    }
}