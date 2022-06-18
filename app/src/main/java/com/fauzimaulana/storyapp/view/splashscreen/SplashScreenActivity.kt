package com.fauzimaulana.storyapp.view.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.fauzimaulana.storyapp.R
import com.fauzimaulana.storyapp.view.main.MainActivity
import com.fauzimaulana.storyapp.view.signup.SignUpActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val screenTime = 3000L
        Handler(mainLooper).postDelayed({
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }, screenTime)
    }
}