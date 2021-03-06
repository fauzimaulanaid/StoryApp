package com.fauzimaulana.storyapp.view.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.fauzimaulana.storyapp.R
import com.fauzimaulana.storyapp.core.utils.CheckNetworkConnection
import com.fauzimaulana.storyapp.core.utils.Utils
import com.fauzimaulana.storyapp.core.vo.Resource
import com.fauzimaulana.storyapp.databinding.ActivitySignUpBinding
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {

    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!

    private val signUpViewModel: SignUpViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
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

    private fun playAnimation() {
        val image = ObjectAnimator.ofFloat(binding.imageView, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupTextView, View.ALPHA, 1f).setDuration(500)
        val name = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameInput = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val email = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailInput = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val password = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordInput = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val signupButton = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)
        val copyright = ObjectAnimator.ofFloat(binding.copyright, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(image, signup, name, nameInput, email, emailInput, password, passwordInput, signupButton, copyright)
            startDelay = 500
            start()
        }
    }

    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val isConnected: Boolean = CheckNetworkConnection().networkCheck(this)
            if (isConnected) {
                val name = binding.nameEditText.text.toString()
                val email = binding.emailEditText.text.toString()
                val password = binding.passwordEditText.text.toString()
                when {
                    name.isEmpty() -> {
                        binding.nameEditTextLayout.error = resources.getString(R.string.name_error_message)
                    }
                    email.isEmpty() -> {
                        binding.emailEditTextLayout.error = resources.getString(R.string.email_error_message)
                    }
                    password.isEmpty() -> {
                        binding.passwordEditTextLayout.error = resources.getString(R.string.password_message)
                    }
                    password.length < 6 || !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                        Toast.makeText(this, resources.getString(R.string.registration_failed), Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        signUpViewModel.registerUser(name, email, password).observe(this) { signUpResult ->
                            when (signUpResult) {
                                is Resource.Loading -> {
                                    binding.contentSignUp.visibility = View.GONE
                                    binding.progressBar.root.visibility = View.VISIBLE
                                }
                                is Resource.Success -> {
                                    binding.progressBar.root.visibility = View.GONE
                                    if (signUpResult.data?.error == false) {
                                        AlertDialog.Builder(this).apply {
                                            setTitle(signUpResult.data.message)
                                            setMessage(resources.getString(R.string.account_created))
                                            setCancelable(false)
                                            setPositiveButton(resources.getString(R.string.ok)) {_, _ ->
                                                finish()
                                            }
                                            create()
                                            show()
                                        }
                                    } else {
                                        Toast.makeText(this, resources.getString(R.string.signup_failed), Toast.LENGTH_SHORT).show()
                                        binding.contentSignUp.visibility = View.VISIBLE
                                    }
                                }
                                is Resource.Error -> {
                                    binding.progressBar.root.visibility = View.GONE
                                    binding.contentSignUp.visibility = View.VISIBLE
                                    Toast.makeText(this, resources.getString(R.string.email_used), Toast.LENGTH_SHORT).show()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}