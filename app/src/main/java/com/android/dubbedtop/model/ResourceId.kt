package com.android.dubbedtop.model

import com.google.gson.annotations.SerializedName

data class ResourceId(
    @SerializedName("kind")
    var kind: String,
    @SerializedName("videoId")
    var videoId: String
)