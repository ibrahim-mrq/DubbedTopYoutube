package com.android.dubbedtop.network.response

import com.android.dubbedtop.model.ListPlay
import com.android.dubbedtop.model.PageInfo
import com.google.gson.annotations.SerializedName

data class PlayListItemResponse(
    @SerializedName("etag")
    var etag: String,
    @SerializedName("items")
    var items: List<ListPlay>,
    @SerializedName("kind")
    var kind: String,
    @SerializedName("nextPageToken")
    var nextPageToken: String,
    @SerializedName("pageInfo")
    var pageInfo: PageInfo
)