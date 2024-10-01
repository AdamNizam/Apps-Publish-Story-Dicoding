package com.example.storyapps.ui.register

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.storyapps.databinding.ActivityRegisterBinding
import com.example.storyapps.di.Injection
import com.example.storyapps.ui.login.LoginActivity
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private val registerViewModel: RegisterViewModel by viewModels {
        RegisterViewModelFactory(Injection.provideUserRepository())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.registerMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Referensi elemen UI dari binding
        binding.loginTextView.setOnClickListener { toLoginPage() }
        val passwordEditText = binding.passwordEditText
        val passwordInputLayout = binding.passwordInputLayout
        val emailEditText = binding.emailEditText
        val emailInputLayout = binding.emailInputLayout
        val registerButton = binding.registerButton


        registerButton.setOnClickListener{ registerUser() }

        addTextWatcher( emailEditText, emailInputLayout, "Email harus diisi dan mengandung @gmail.com"
        ) { inputText ->
            inputText.contains("@gmail.com")
        }
        addTextWatcher( passwordEditText, passwordInputLayout,"Password tidak boleh kurang dari 8 karakter"
        ) { inputText ->
            inputText.length >= 8
        }
    }
    private fun registerUser() {
        val fullName = binding.nameEditText.text.toString().trim()
        val email = binding.emailEditText.text.toString().trim()
        val password = binding.passwordEditText.text.toString().trim()

        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Silakan isi semua kolom", Toast.LENGTH_SHORT).show()
            return
        }

        binding.registerButton.isEnabled = false
        binding.loadingProgressBar.visibility = View.VISIBLE
        val handler = Handler(mainLooper)
        handler.postDelayed({
            registerViewModel.register(fullName, email, password) { response ->
                runOnUiThread {
                    binding.registerButton.isEnabled = true
                    if (response.error) {
                        binding.nameEditText.text?.clear()
                        binding.emailEditText.text?.clear()
                        binding.passwordEditText.text?.clear()
                        Toast.makeText(this, "Register failed: ${response.message}", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.nameEditText.text?.clear()
                        binding.emailEditText.text?.clear()
                        binding.passwordEditText.text?.clear()
                        Toast.makeText(this, "Register successful!", Toast.LENGTH_SHORT).show()
                        toLoginPage()
                    }
                }
            }
        }, 1000)
    }

    private fun addTextWatcher(
        editText: EditText,
        inputLayout: TextInputLayout,
        errorMessage: String,
        validation: (String) -> Boolean
    ) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputText = s.toString()
                if (!validation(inputText)) {
                    inputLayout.error = errorMessage
                } else {
                    inputLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun toLoginPage(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}