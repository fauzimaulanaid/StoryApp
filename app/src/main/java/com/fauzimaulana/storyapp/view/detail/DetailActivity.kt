package com.fauzimaulana.storyapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ceylonlabs.imageviewpopup.ImagePopup
import com.fauzimaulana.storyapp.R
import com.fauzimaulana.storyapp.core.domain.model.StoryModel
import com.fauzimaulana.storyapp.databinding.ActivityDetailBinding
import com.fauzimaulana.storyapp.databinding.ContentDetailBinding
import java.text.SimpleDateFormat
import java.util.*

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

        val imagePopUp = ImagePopup(this)
        imagePopUp.initiatePopupWithGlide(story?.photoUrl)
        binding.storyImageView.setOnClickListener {
            imagePopUp.viewPopup()
        }
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
            val calendar = Calendar.getInstance()
            calendar.time = date!!
            calendar.add(Calendar.HOUR, 7)

            binding.tvDateCreated.text = resources.getString(R.string.date_created_wib, createDate(calendar.time))
            binding.tvDateCreatedOnServer.text = resources.getString(R.string.date_created_server_time, createDate(date))

            binding.tvCreatedFrom.text = resources.getString(R.string.created_from, story.lat, story.lon)
        }
    }

    @Suppress("DEPRECATION")
    private fun createDate(date: Date): String {
        val day = when(date.day) {
            0 -> "Sun"
            1 -> "Mon"
            2 -> "Tue"
            3 -> "Wed"
            4 -> "Thu"
            5 -> "Fri"
            6 -> "Sat"
            else -> "Unknown"
        }

        val month = when(date.month) {
            0 -> "Jan"
            1 -> "Feb"
            2 -> "Mar"
            3 -> "Apr"
            4 -> "May"
            5 -> "Jun"
            6 -> "Jul"
            7 -> "Aug"
            8 -> "Sep"
            9 -> "Oct"
            10 -> "Nov"
            11 -> "Dec"
            else -> "Unknown"
        }

        val dateCreated = date.date

        val year = date.year % 100

        val hours = if (date.hours.toString().length == 1) "0${date.hours}" else date.hours
        val minutes = if (date.minutes.toString().length == 1) "0${date.minutes}" else date.minutes

        return "$day $month $dateCreated 20$year at $hours.$minutes"
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