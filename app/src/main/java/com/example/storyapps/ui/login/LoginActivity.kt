package com.example.storyapps.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.storyapps.databinding.ActivityLoginBinding
import com.example.storyapps.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.loginMain) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val passwordEditText = binding.passwordEditText
        val passwordInputLayout = binding.passwordInputLayout
        val emailEditText = binding.emailEditText
        val emailInputLayout = binding.emailInputLayout

        emailEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.toString().contains("@gmail.com")) {
                    emailInputLayout.error = "Email harus Di isi"
                } else {
                    emailInputLayout.error = null
                }
            }

            override fun afterTextChanged(s: Editable?) { }
        })

        passwordEditText.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().length < 8) {
                    passwordInputLayout.error = "Password tidak boleh kurang dari 8 karakter"
                } else {
                    passwordInputLayout.error = null
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.registerTextView.setOnClickListener { toRegisterPage() }
    }

    private fun toRegisterPage(){
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}