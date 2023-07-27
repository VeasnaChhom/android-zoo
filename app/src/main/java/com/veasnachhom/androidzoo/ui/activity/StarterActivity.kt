package com.veasnachhom.androidzoo.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.veasnachhom.androidzoo.R
import com.veasnachhom.androidzoo.attractionPlace.ui.activity.HomeActivity

class StarterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_starter)
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}