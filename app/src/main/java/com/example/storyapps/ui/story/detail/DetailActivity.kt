package com.example.storyapps.ui.story.detail

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.storyapps.databinding.ActivityDetailBinding
import com.example.storyapps.ui.story.adapter.Story

@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.detailActivity) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val story: Story? = intent.getParcelableExtra("EXTRA_STORY")
        Log.d("DetailActivity", " Data yang di dapat$story")
        story.let {
            Glide.with(this)
                .load(it?.photoUrl)
                .into(binding.profileImage)
            binding.fullName.text = it?.name
            binding.description.text = it?.description
            binding.date.text = it?.createdAt
        }

    }
}