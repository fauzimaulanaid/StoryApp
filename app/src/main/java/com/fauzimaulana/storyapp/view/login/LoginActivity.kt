package com.fauzimaulana.storyapp.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.fauzimaulana.storyapp.R
import com.fauzimaulana.storyapp.databinding.ActivityLoginBinding
import com.fauzimaulana.storyapp.core.domain.model.UserModel
import com.fauzimaulana.storyapp.core.utils.CheckNetworkConnection
import com.fauzimaulana.storyapp.core.utils.Utils
import com.fauzimaulana.storyapp.core.vo.Resource
import com.fauzimaulana.storyapp.view.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
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
            val isConnected: Boolean = CheckNetworkConnection().networkCheck(this)
            if (isConnected) {
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                when {
                    email.isEmpty() -> {
                        binding.emailEditTextLayout.error = resources.getString(R.string.email_error_message)
                    }
                    password.isEmpty() -> {
                        binding.passwordEditTextLayout.error = resources.getString(R.string.hint_password)
                    }
                    else -> {
                        loginViewModel.userLogin(email, password).observe(this) { loginResult ->
                            when (loginResult) {
                                is Resource.Loading -> {
                                    binding.contentLogin.visibility = View.GONE
                                    binding.progressBar.root.visibility = View.VISIBLE
                                }
                                is Resource.Success -> {
                                    binding.progressBar.root.visibility = View.GONE
                                    if (loginResult.data?.error == false) {
                                        loginViewModel.saveUser(UserModel(loginResult.data.loginResult.name, email, password, loginResult.data.loginResult.token, true))
                                        AlertDialog.Builder(this).apply {
                                            setTitle(resources.getString(R.string.congratulations))
                                            setMessage(resources.getString(R.string.login_success))
                                            setCancelable(false)
                                            setPositiveButton(resources.getString(R.string.ok)) {_, _ ->
                                                val intent = Intent(context, MainActivity::class.java)
                                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                                startActivity(intent)
                                                finish()
                                            }
                                            create()
                                            show()
                                        }
                                    } else {
                                        Toast.makeText(this, loginResult.data?.message, Toast.LENGTH_SHORT).show()
                                        binding.contentLogin.visibility = View.VISIBLE
                                    }
                                }
                                is Resource.Error -> {
                                    binding.progressBar.root.visibility = View.GONE
                                    binding.contentLogin.visibility = View.VISIBLE
                                    Toast.makeText(this, resources.getString(R.string.invalid_email_password), Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            } else {
                Utils.showAlertNoInternet(this)
            }
        }
    }

    private fun playAnimation() {
        val image = ObjectAnimator.ofFloat(binding.imageViewStory, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginTextView, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailInput = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordInput = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val loginButton = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val copyright = ObjectAnimator.ofFloat(binding.copyright, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(image, login, email, emailInput, password, passwordInput, loginButton, copyright)
            startDelay = 500
            start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}