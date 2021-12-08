package com.android.dubbedtop.model

import com.google.gson.annotations.SerializedName

data class PageInfo(
    @SerializedName("resultsPerPage")
    var resultsPerPage: Int,
    @SerializedName("totalResults")
    var totalResults: Int
)