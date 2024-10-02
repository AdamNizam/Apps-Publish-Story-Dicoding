package com.example.storyapps.ui.story

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapps.databinding.ActivityStoryBinding
import com.example.storyapps.di.Injection
import com.example.storyapps.ui.story.adapter.StoryAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private lateinit var storyAdapter: StoryAdapter
    private val storyViewModel: StoryViewModel by viewModels {
        StoryViewModelFactory(Injection.provideStoryRepository(this))
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.mainStory) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        storyAdapter = StoryAdapter(emptyList())
        binding.recyclerView.adapter = storyAdapter

        observeStories()
    }

    private fun observeStories() {
        storyViewModel.getStories().observe(this) { response ->
            if (response?.listStory != null) {
                storyAdapter = StoryAdapter(response.listStory.map { it!! })
                binding.recyclerView.adapter = storyAdapter
            } else {
                // Tangani kesalahan atau tampilkan pesan kesalahan di sini
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            storyViewModel.getStories()
        }
    }
}
