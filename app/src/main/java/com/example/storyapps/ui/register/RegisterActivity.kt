package com.example.storyapps.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.storyapps.databinding.ActivityRegisterBinding
import com.example.storyapps.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

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

        binding.loginTextView.setOnClickListener { toLoginPage() }
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


    }

    private fun toLoginPage(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}