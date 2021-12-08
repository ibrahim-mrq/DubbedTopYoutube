package com.android.dubbedtop.model

import com.google.gson.annotations.SerializedName

data class Thumbnails(
    @SerializedName("default")
    var default: Default,
    @SerializedName("high")
    var high: High,
    @SerializedName("maxres")
    var maxres: Maxres,
    @SerializedName("medium")
    var medium: Medium,
    @SerializedName("standard")
    var standard: Standard
)