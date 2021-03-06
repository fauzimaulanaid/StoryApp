package com.fauzimaulana.storyapp.core.ui

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fauzimaulana.storyapp.R
import com.fauzimaulana.storyapp.core.domain.model.StoryModel
import com.fauzimaulana.storyapp.databinding.ItemListStoryNewBinding
import com.fauzimaulana.storyapp.view.detail.DetailActivity
import java.text.SimpleDateFormat
import java.util.*

class StoryAdapter: ListAdapter<StoryModel, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {

    @Suppress("SimpleDateFormat")
    class StoryViewHolder(private val binding: ItemListStoryNewBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(story: StoryModel) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(storyImageView)
                tvName.text = story.name

                val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val timeCreatedServerTime = formatter.parse(story.createAt)
                //Karena jam di server lebih lambat 7 jam dari WIB, jadi saya mencari real hours yaitu GMT+7
                val calendar = Calendar.getInstance()
                calendar.time = timeCreatedServerTime!!
                calendar.add(Calendar.HOUR, 7)

                val currentDate = Date()

                val diff = currentDate.time - calendar.time.time
                val seconds = diff / 1000
                val minutes = seconds / 60
                val hours = minutes / 60

                val modMinutes = minutes % 60

                val resultCreatedAt: String = if (hours.toString() == "0") {
                    itemView.context.getString(R.string.story_created_at_only_minutes, modMinutes)
                } else {
                    itemView.context.getString(R.string.story_created_at, hours, modMinutes)
                }

                val finalCreatedAt: String = if (seconds <= 60) itemView.context.getString(R.string.just_now) else resultCreatedAt

                tvCreatedAt.text = finalCreatedAt

                itemView.setOnClickListener {
                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(tvName, "name"),
                            Pair(tvCreatedAt, "created"),
                            Pair(storyImageView, "story")
                        )

                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA, story)
                    intent.putExtra(DetailActivity.EXTRA_INFO, finalCreatedAt)
                    itemView.context.startActivity(intent, optionsCompat.toBundle())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val itemListStoryBinding = ItemListStoryNewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(itemListStoryBinding)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        val story = getItem(position)
        holder.bind(story)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<StoryModel>() {
            override fun areItemsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: StoryModel, newItem: StoryModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}