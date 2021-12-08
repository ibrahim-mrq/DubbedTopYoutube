package com.android.dubbedtop.network.response

import com.android.dubbedtop.model.*
import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("kind")
    var kind: String,
    @SerializedName("etag")
    var etag: String,
    @SerializedName("nextPageToken")
    var nextPageToken: String,
    @SerializedName("prevPageToken")
    var prevPageToken: String?,
    @SerializedName("regionCode")
    var regionCode: String?,
    @SerializedName("pageInfo")
    var pageInfo: PageInfo,
    @SerializedName("items")
    var items: List<Search>,
)