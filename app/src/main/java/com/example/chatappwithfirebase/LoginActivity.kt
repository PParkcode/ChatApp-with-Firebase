package com.example.chatappwithfirebase

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig

class LoginActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        var login = findViewById<Button>(R.id.loginActivity_button_login)
        var signup= findViewById<Button>(R.id.loginActivity_button_signup)

        var splash_background = mFirebaseRemoteConfig.getString("splash_background")

        //window.statusBarColor(Color.parseColor(splash_background))

        login.setBackgroundColor(Color.parseColor(splash_background))
        signup.setBackgroundColor(Color.parseColor(splash_background))

        signup.setOnClickListener{
            startActivity(Intent(this,SignupActivity::class.java))
        }
    }
}