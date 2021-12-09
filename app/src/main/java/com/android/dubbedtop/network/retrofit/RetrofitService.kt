package com.android.dubbedtop.network.retrofit

import com.android.dubbedtop.network.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface RetrofitService {

    @GET("playlists")
    suspend fun playList(
        @Query("part") part: String?,
        @Query("key") key: String?,
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: Int?,
        @Query("pageToken") pageToken: String?,
    ): Response<PlayListResponse>

    @GET("search")
    suspend fun search(
        @Query("part") part: String?,
        @Query("key") key: String?,
        @Query("channelId") channelId: String,
        @Query("order") order: String,
        @Query("type") type: String,
        @Query("maxResults") maxResults: Int?,
        @Query("pageToken") pageToken: String?,
    ): Response<SearchResponse>

}
