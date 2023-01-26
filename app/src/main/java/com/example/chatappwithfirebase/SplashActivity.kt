package com.example.chatappwithfirebase

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

class SplashActivity :AppCompatActivity(){
    private lateinit var linearLayout: LinearLayout
    lateinit var remoteConfig: FirebaseRemoteConfig
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        linearLayout = findViewById(R.id.splash_activity_linearLayout)

        remoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
            mapOf(
                SPLASH_BACKGROUND to "#000000",
                SPLASH_MESSAGE_CAPS to "false",
                SPLASH_MESSAGE to "welcome to my awesome app!~"
            )
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        //remoteConfig.setDefaultsAsync(R.xml.default_config)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d("tag1", "Config params updated: $updated")
                    Toast.makeText(this, "Fetch and activate succeeded",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Fetch failed",
                        Toast.LENGTH_SHORT).show()
                }
                displayMessage()

            }
    }
    fun displayMessage(){
        Log.d("tag1","displayMessage 실행")
        val splash_background = remoteConfig.getString("splash_background")
        val caps:Boolean = remoteConfig.getBoolean("splash_message_caps")
        val splash_message= remoteConfig.getString("splash_message")

        linearLayout.setBackgroundColor(Color.parseColor(splash_background))

        if(caps){
            val builder:AlertDialog.Builder =  AlertDialog.Builder(this)
            builder.setMessage(splash_message).setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                finish()
            })
            builder.create().show()
        } else{
            startActivity(Intent(this,LoginActivity::class.java))
        }

    }
    companion object{
        const val SPLASH_BACKGROUND="splash_background"
        const val SPLASH_MESSAGE_CAPS="splash_message_caps"
        const val SPLASH_MESSAGE="splash_message"
    }


}