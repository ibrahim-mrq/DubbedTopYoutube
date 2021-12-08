package com.android.dubbedtop.model

import com.google.gson.annotations.SerializedName

data class Localized(
    @SerializedName("description")
    var description: String,
    @SerializedName("title")
    var title: String
)