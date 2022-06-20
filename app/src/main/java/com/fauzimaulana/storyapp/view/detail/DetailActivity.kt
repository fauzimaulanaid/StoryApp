package com.fauzimaulana.storyapp.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fauzimaulana.storyapp.R
import com.fauzimaulana.storyapp.core.domain.model.StoryModel
import com.fauzimaulana.storyapp.databinding.ActivityDetailBinding
import com.fauzimaulana.storyapp.databinding.ContentDetailBinding

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
            binding.tvDateCreated.text = story.createAt
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