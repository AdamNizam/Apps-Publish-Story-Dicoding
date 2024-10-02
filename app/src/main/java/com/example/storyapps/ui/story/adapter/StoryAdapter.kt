package com.example.storyapps.ui.story.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.storyapps.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.storyapps.data.api.response.ListStoryItem



class StoryAdapter(private var stories: List<ListStoryItem>) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    class StoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val floatingActionButton: FloatingActionButton = itemView.findViewById(R.id.floating_action_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_story, parent, false)
        return StoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = stories[position]
        holder.titleTextView.text = story.name
        holder.descriptionTextView.text = story.description
        Glide.with(holder.imageView.context)
            .load(story.photoUrl)
            .into(holder.imageView)
        holder.floatingActionButton.setOnClickListener {
            // Handle favorite button click, e.g., save story to favorites
        }
    }

    override fun getItemCount(): Int = stories.size

//    // Metode untuk memperbarui daftar cerita
//    fun updateStories(newStories: List<Story>) {
//        stories = newStories
////        notifyDataSetChanged()
//    }
}
