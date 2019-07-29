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

class LoginFragment: Fragment() {

    private lateinit var binding: FragmentLoginsBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_logins,
            container,
            false
        )

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        setFacebookButton()
        setFacebookItems()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
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

    private fun setFacebookItems() {
        viewModel.accessToken.observe(this, Observer {accessToken ->
            if (accessToken != null) {
                binding.facebookToken.text = getString(R.string.facebook_token, accessToken.token)
            } else {
                binding.facebookToken.text = getString(R.string.facebook_token, "-")
            }
        })

        viewModel.isLoggedIn.observe(this, Observer { isLoggedIn ->
            if (isLoggedIn) {
                binding.isLoggedInImage.setBackgroundColor(Color.GREEN)
            } else {
                binding.isLoggedInImage.setBackgroundColor(Color.RED)
            }
        })

        binding.checkFbStatusButton.setOnClickListener {
            viewModel.checkFacebookLoginStatus()
        }
    }
}