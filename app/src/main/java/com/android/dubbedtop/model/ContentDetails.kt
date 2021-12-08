package com.android.dubbedtop.model

import com.google.gson.annotations.SerializedName

data class ContentDetails(
    @SerializedName("itemCount")
    var itemCount: Int,
    @SerializedName("videoId")
    var videoId: String,
    @SerializedName("videoPublishedAt")
    var videoPublishedAt: String
)