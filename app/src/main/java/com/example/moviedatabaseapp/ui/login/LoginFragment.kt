package com.example.moviedatabaseapp.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.moviedatabaseapp.R
import com.example.moviedatabaseapp.databinding.FragmentLoginsBinding
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginFragment: Fragment() {

    private lateinit var binding: FragmentLoginsBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 900

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_logins,
            container,
            false
        )

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        googleSignInClient = GoogleSignIn.getClient(activity!!, viewModel.gso)

        setFacebookButton()
        setFacebookItems()
        setGoogleButton()
        setGoogleItems()

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("FacebookLogin", "onActivityResult")
        viewModel.callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        // Google sign in intent result
        if (requestCode == RC_SIGN_IN) {
            Log.i("GoogleLogin", "on activity result")
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun setFacebookButton() {
        val loginButton = binding.loginButton
        loginButton.setPermissions("email")
        loginButton.fragment = this

        // Implement the callback in one of both, either in LoginButton or in LoginManager
        registerCallbackLoginButton(loginButton)
        //registerCallbackLoginManager()
    }

    private fun registerCallbackLoginButton(loginButton: LoginButton) {
        loginButton.registerCallback(viewModel.callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                if (result != null) {
                    Log.i("FacebookLogin", "result - access token - app id: ${result.accessToken.applicationId}")
                    Log.i("FacebookLogin", "result - access token - token: ${result.accessToken.token}")
                    Log.i("FacebookLogin", "result - access token - user id: ${result.accessToken.userId}")
                    Log.i("FacebookLogin", "result - recently denied permissions: ${result.recentlyDeniedPermissions.size}")
                    Log.i("FacebookLogin", "result - recently granted permissions: ${result.recentlyGrantedPermissions.size}")
                } else {
                    Log.i("FacebookLogin", "registerCallback result = null")
                }
            }

            override fun onCancel() {
                Log.i("FacebookLogin", "registerCallback onCancel")
            }

            override fun onError(error: FacebookException?) {
                Log.i("FacebookLogin", "registerCallback onError - exception: ${error?.message}")
            }
        })
    }

    private fun registerCallbackLoginManager() {
        LoginManager.getInstance().registerCallback(viewModel.callbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                if (result != null) {
                    Log.i("FacebookLogin", "onSuccess")
                } else {
                    Log.i("FacebookLogin", "registerCallback result = null")
                }
            }

            override fun onCancel() {
                Log.i("FacebookLogin", "registerCallback onCancel")
            }

            override fun onError(error: FacebookException?) {
                Log.i("FacebookLogin", "registerCallback onError - exception: ${error?.message}")
            }
        })
    }

    private fun setFacebookItems() {
        viewModel.accessTokenFb.observe(this, Observer { accessToken ->
            if (accessToken != null) {
                binding.facebookToken.text = getString(R.string.facebook_token, accessToken.token)
            } else {
                binding.facebookToken.text = getString(R.string.facebook_token, "-")
            }
        })

        viewModel.isLoggedInFb.observe(this, Observer { isLoggedIn ->
            if (isLoggedIn) {
                binding.isLoggedInFbImage.setBackgroundColor(Color.GREEN)
            } else {
                binding.isLoggedInFbImage.setBackgroundColor(Color.RED)
            }
        })
    }

    private fun setGoogleButton() {
        val googleButton = binding.signInButton
        googleButton.setSize(SignInButton.SIZE_STANDARD)
        googleButton.setOnClickListener {
            Log.i("GoogleLogin", "on click listener")
            googleSignIn()
        }
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun googleSignOut() {
        googleSignInClient.signOut()
            .addOnCompleteListener {
                viewModel.setGoogleAccount(null)
            }
    }

    // Take this function to the viewmodel
    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(ApiException::class.java)
            Log.i("GoogleLogin", "account - email: ${account?.email}")
            Log.i("GoogleLogin", "account - id: ${account?.id}")
            viewModel.setGoogleAccount(account)
        } catch (e: ApiException) {
            Log.i("GoogleLogin", "signInResult failed code: ${e.statusCode}")
        }
    }

    private fun setGoogleItems() {
        val account = GoogleSignIn.getLastSignedInAccount(this.context)
        viewModel.setGoogleAccount(account)

        viewModel.googleAccount.observe(this, Observer {googleAccount ->
            Log.i("GoogleLogin", "google account changed")
            if (googleAccount != null) {
                binding.googleEmail.text = getString(R.string.google_email, googleAccount.email)
                binding.googleId.text = getString(R.string.google_id, googleAccount.id)
                binding.isLoggedInGgImage.setBackgroundColor(Color.GREEN)
            } else {
                binding.googleEmail.text = getString(R.string.google_email, "-")
                binding.googleId.text = getString(R.string.google_id, "-")
                binding.isLoggedInGgImage.setBackgroundColor(Color.RED)
            }
        })

        binding.signOutGgButton.setOnClickListener {
            googleSignOut()
        }
    }
}