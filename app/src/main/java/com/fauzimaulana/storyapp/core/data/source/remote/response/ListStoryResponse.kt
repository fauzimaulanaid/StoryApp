package com.fauzimaulana.storyapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListStoryResponse(
	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("listStory")
	val listStory: List<StoryResponse>
)

data class StoryResponse(
	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("lat")
	val lat: Double,

	@field:SerializedName("lon")
	val lon: Double
)
