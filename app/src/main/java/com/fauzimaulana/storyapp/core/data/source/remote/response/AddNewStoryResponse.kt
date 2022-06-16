package com.fauzimaulana.storyapp.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class AddNewStoryResponse(
	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)
