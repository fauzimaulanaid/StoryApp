package com.fauzimaulana.storyapp.view.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.fauzimaulana.storyapp.databinding.ActivityWelcomeBinding
import com.fauzimaulana.storyapp.view.login.LoginActivity
import com.fauzimaulana.storyapp.view.signup.SignUpActivity
import kotlin.math.log

class WelcomeActivity : AppCompatActivity() {

    private var _binding: ActivityWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityWelcomeBinding.inflate(layoutInflater)
//        installSplashScreen().setOnExitAnimationListener {splashScreenView ->
//            val alpha = ObjectAnimator.ofFloat(
//                splashScreenView.view,
//                View.ALPHA,
//                11f,
//                0f
//            )
//            alpha.duration = 3000L
//            alpha.doOnEnd { splashScreenView.remove() }
//            alpha.start()
//        }
        setContentView(binding.root)
        setupView()
        playAnimation()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.signupButton.setOnClickListener {
            val intent = Intent(this@WelcomeActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playAnimation() {
        val logo = ObjectAnimator.ofFloat(binding.storyAppLogo, View.ALPHA, 1f).setDuration(500)
        val welcome = ObjectAnimator.ofFloat(binding.welcomeTextView, View.ALPHA, 1f).setDuration(500)
        val welcomeMessage = ObjectAnimator.ofFloat(binding.welcomeMessageTextView, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)
        val copyright = ObjectAnimator.ofFloat(binding.copyright, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(logo, welcome, welcomeMessage, login, signup, copyright)
            startDelay = 500
            start()
        }
    }
}