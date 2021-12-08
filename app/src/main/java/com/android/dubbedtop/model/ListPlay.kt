package com.android.dubbedtop.model

import com.google.gson.annotations.SerializedName

data class ListPlay(
    @SerializedName("kind")
    var kind: String,
    @SerializedName("etag")
    var etag: String,
    @SerializedName("snippet")
    var snippet: Snippet,
    @SerializedName("contentDetails")
    var contentDetails: ContentDetails,
    @SerializedName("id")
    var id: String,
)