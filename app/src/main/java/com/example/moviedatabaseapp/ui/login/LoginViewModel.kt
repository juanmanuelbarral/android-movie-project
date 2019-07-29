package com.example.moviedatabaseapp.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.facebook.AccessTokenTracker
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class LoginViewModel : ViewModel() {

    val callbackManager: CallbackManager = CallbackManager.Factory.create()
    private val accessTokenTracker: AccessTokenTracker
    val gso: GoogleSignInOptions

    private val _accessTokenFb = MutableLiveData<AccessToken>()
    val accessTokenFb: LiveData<AccessToken>
        get() = _accessTokenFb

    private val _isLoggedInFb = MutableLiveData<Boolean>()
    val isLoggedInFb: LiveData<Boolean>
        get() = _isLoggedInFb

    private val _googleAccount = MutableLiveData<GoogleSignInAccount>()
    val googleAccount: LiveData<GoogleSignInAccount>
        get() = _googleAccount

    init {
        // FACEBOOK
        _accessTokenFb.value = AccessToken.getCurrentAccessToken()
        _isLoggedInFb.value = _accessTokenFb.value != null && !_accessTokenFb.value!!.isExpired
        accessTokenTracker = object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
                _accessTokenFb.value = currentAccessToken
                _isLoggedInFb.value = currentAccessToken != null && !currentAccessToken.isExpired
            }
        }

        // GOOGLE
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("LoginViewModel", "onCleared called")
    }

    fun setGoogleAccount(account: GoogleSignInAccount?) {
        Log.i("GoogleLogin", "set google account - account != null: ${account != null}")
        _googleAccount.value = account
    }
}