package com.android.dubbedtop.model

import com.google.gson.annotations.SerializedName

data class Snippet(
    @SerializedName("channelId")
    var channelId: String,
    @SerializedName("channelTitle")
    var channelTitle: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("localized")
    var localized: Localized,
    @SerializedName("publishedAt")
    var publishedAt: String,
    @SerializedName("thumbnails")
    var thumbnails: Thumbnails,
    @SerializedName("title")
    var title: String,
    @SerializedName("playlistId")
    var playlistId: String,
    @SerializedName("position")
    var position: Int,
    @SerializedName("resourceId")
    var resourceId: ResourceId,
    @SerializedName("videoOwnerChannelId")
    var videoOwnerChannelId: String,
    @SerializedName("videoOwnerChannelTitle")
    var videoOwnerChannelTitle: String,
    @SerializedName("liveBroadcastContent")
    var liveBroadcastContent: String,
    @SerializedName("publishTime")
    var publishTime: String,
)