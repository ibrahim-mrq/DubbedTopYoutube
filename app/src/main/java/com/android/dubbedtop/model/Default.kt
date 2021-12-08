package com.android.dubbedtop.model

import com.google.gson.annotations.SerializedName

data class Default(
    @SerializedName("height")
    var height: Int,
    @SerializedName("url")
    var url: String,
    @SerializedName("width")
    var width: Int
)