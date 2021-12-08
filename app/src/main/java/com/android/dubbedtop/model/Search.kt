package com.android.dubbedtop.model

import com.google.gson.annotations.SerializedName

data class Search(
    @SerializedName("kind")
    var kind: String,
    @SerializedName("etag")
    var etag: String,
    @SerializedName("id")
    var id: Id,
    @SerializedName("snippet")
    var snippet: Snippet,
)