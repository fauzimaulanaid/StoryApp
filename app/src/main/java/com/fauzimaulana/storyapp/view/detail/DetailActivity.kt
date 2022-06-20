package com.fauzimaulana.storyapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fauzimaulana.storyapp.R
import com.fauzimaulana.storyapp.core.domain.model.StoryModel
import com.fauzimaulana.storyapp.databinding.ActivityDetailBinding
import com.fauzimaulana.storyapp.databinding.ContentDetailBinding
import java.text.SimpleDateFormat

class DetailActivity : AppCompatActivity() {

    private var _binding: ContentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        _binding = activityDetailBinding.detailContent
        setContentView(activityDetailBinding.root)

        setSupportActionBar(activityDetailBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val story = intent.getParcelableExtra<StoryModel>(EXTRA_DATA)
        val createdAt = intent.extras?.getString(EXTRA_INFO)
        showDetailStory(story, createdAt!!)
    }

    @Suppress("SimpleDateFormat", "DEPRECATION")
    private fun showDetailStory(story: StoryModel?, createdAt: String) {
        story?.let {
            binding.tvName.text = story.name
            binding.tvCreatedAt.text = createdAt
            Glide.with(this)
                .load(story.photoUrl)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
                .into(binding.storyImageView)
            binding.tvDescription.text = story.description
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val date = formatter.parse(story.createAt)

            val day = when(date!!.day) {
                0 -> "Sunday"
                1 -> "Monday"
                2 -> "Tuesday"
                3 -> "Wednesday"
                4 -> "Thursday"
                5 -> "Friday"
                6 -> "Saturday"
                else -> "Unknown"
            }

            val dateCreated = date.date

            val month = when(date.month) {
                0 -> "January"
                1 -> "February"
                2 -> "March"
                3 -> "April"
                4 -> "May"
                5 -> "June"
                6 -> "July"
                7 -> "August"
                8 -> "September"
                9 -> "October"
                10 -> "November"
                11 -> "December"
                else -> "Unknown"
            }

            val year = date.year % 100

            val hours = if (date.hours.toString().length == 1) "0${date.hours}" else date.hours
            val minutes = if (date.minutes.toString().length == 1) "0${date.minutes}" else date.minutes

            binding.tvDateCreated.text = "$day, $dateCreated $month 20$year at $hours.$minutes WIB"
            binding.tvCreatedFrom.text = resources.getString(R.string.created_from, story.lat, story.lon)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val EXTRA_INFO = "extra_info"
    }
}