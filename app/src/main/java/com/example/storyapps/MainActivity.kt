package com.example.storyapps

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.asLiveData
import com.example.storyapps.databinding.ActivityMainBinding
import com.example.storyapps.di.Injection
import com.example.storyapps.repository.UserLoginRepository
import com.example.storyapps.ui.login.LoginActivity
import com.example.storyapps.ui.register.RegisterActivity
import com.example.storyapps.ui.story.StoryActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var userLoginRepository: UserLoginRepository

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         userLoginRepository = Injection.provideUserLoginRepository(this)

         userLoginRepository.getToken().asLiveData().observe(this) { token ->
             if (!token.isNullOrEmpty()) {
                 navigateTo(StoryActivity::class.java)
                 finish()
             }
         }

        binding.button.setOnClickListener { navigateTo(LoginActivity::class.java) }
        binding.button1.setOnClickListener { navigateTo(RegisterActivity::class.java) }
    }

    private fun <T> Activity.navigateTo(targetActivity: Class<T>) {
        val intent = Intent(this, targetActivity)
        startActivity(intent)
    }

}


