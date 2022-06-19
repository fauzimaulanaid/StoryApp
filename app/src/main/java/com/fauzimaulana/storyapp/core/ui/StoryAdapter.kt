package com.fauzimaulana.storyapp.core.ui

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fauzimaulana.storyapp.R
import com.fauzimaulana.storyapp.core.domain.model.StoryModel
import com.fauzimaulana.storyapp.databinding.ItemListStoryBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class StoryAdapter: ListAdapter<StoryModel, StoryAdapter.StoryViewHolder>(DIFF_CALLBACK) {
    class StoryViewHolder(private val binding: ItemListStoryBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(story: StoryModel) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(story.photoUrl)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                    .into(storyImageView)
                tvName.text = story.name

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val current = LocalDateTime.now()
                    val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
//                    val formatter = LocalDate.parse(story.createAt, dateTimeFormatter)
                    tvCreatedAt.text = current.format(dateTimeFormatter)
//                    val day = formatter.dayOfWeek.toString()
//                    val date = formatter.dayOfMonth.toString()
//                    val month = formatter.month.toString()
//                    val year = formatter.year.toString()
                } else {
                    val date = Date()
                    val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                    tvCreatedAt.text = dateTimeFormatter.format(date)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        val itemListStoryBinding = ItemListStoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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