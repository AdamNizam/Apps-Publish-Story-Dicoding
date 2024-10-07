package com.example.storyapps.ui.story

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapps.databinding.ActivityStoryBinding
import com.example.storyapps.di.Injection
import com.example.storyapps.ui.story.adapter.StoryAdapter
import kotlinx.coroutines.launch
import com.example.storyapps.R
import com.example.storyapps.repository.UserLoginRepository
import com.example.storyapps.ui.login.LoginActivity
import com.example.storyapps.ui.story.adapter.CarouselAdapter
import com.example.storyapps.ui.story.detail.DetailActivity
import com.example.storyapps.ui.story.upload.UploadActivity
import com.google.android.material.carousel.CarouselLayoutManager

class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var carouselAdapter: CarouselAdapter
    private lateinit var userLoginRepository: UserLoginRepository
    private val storyViewModel: StoryViewModel by viewModels {
        StoryViewModelFactory(Injection.provideStoryRepository(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.storyActivity) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userLoginRepository = Injection.provideUserLoginRepository(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.carouselRecyclerView.layoutManager = CarouselLayoutManager()

        carouselAdapter = CarouselAdapter(emptyList())
        storyAdapter = StoryAdapter(emptyList())

        binding.recyclerView.adapter = storyAdapter
        binding.carouselRecyclerView.adapter = carouselAdapter

        observeStories()

        setSupportActionBar(binding.topAppBar)
    }

    private fun observeStories() {
        storyViewModel.stories.observe(this) { storyList ->
            storyAdapter.updateStories(storyList ?: emptyList())
            carouselAdapter.updateStories(storyList ?: emptyList())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.addStory -> {
                movePage(UploadActivity::class.java)
            }
            R.id.listItemFavorit -> {
                movePage(DetailActivity::class.java)
            }
            R.id.logoutButton -> { logout()}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun logout() {
        lifecycleScope.launch {
            userLoginRepository.deleteToken()
            movePage(LoginActivity::class.java)
            finish()
        }
    }

    private fun <T> Activity.movePage(objectiveActivity: Class<T>){
        val intent = Intent(this, objectiveActivity)
        startActivity(intent)

    }

    override fun onResume() {
        super.onResume()
        storyViewModel.fetchStories()
    }


}
