package com.android.dubbedtop.network.retrofit

import android.content.Context
import android.util.Log
import androidx.annotation.Nullable
import com.android.dubbedtop.R
import com.android.dubbedtop.helpers.Constants
import com.android.dubbedtop.network.response.*
import com.orhanobut.hawk.Hawk
import kotlinx.coroutines.*
import retrofit2.HttpException

class ApiRequests {

    private val service: RetrofitService = RetrofitClient.makeRetrofitService()

    fun playList(
        pageToken: String?,
        context: Context,
        @Nullable result: Results<PlayListResponse?>
    ): Job {
        val handler = CoroutineExceptionHandler { _, exception ->
            result.onFailure(exception.message!!)
        }
        return CoroutineScope(Dispatchers.Main).launch(handler) {
            val response = service.playList(
                Constants.TYPE_PART,
                Constants.TYPE_KEY,
                Constants.TYPE_CHANNEL_ID,
                Constants.TYPE_MAX_RESULTS,
                pageToken,
            )
            Log.e("RESPONSE", response.toString());
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        result.onSuccess(response.body())
                    } else {
                        result.onFailure(context.resources.getString(R.string.connection_error))
                    }
                } catch (e: HttpException) {
                    result.onException(e)
                }
            }
        }
    }

    fun search(
        pageToken: String?,
        context: Context,
        @Nullable result: Results<SearchResponse?>
    ): Job {
        val handler = CoroutineExceptionHandler { _, exception ->
            result.onFailure(exception.message!!)
        }
        return CoroutineScope(Dispatchers.Main).launch(handler) {
            val response = service.search(
                Constants.TYPE_PART_SNIPPET,
                Constants.TYPE_KEY,
                Constants.TYPE_CHANNEL_ID,
                Constants.TYPE_ORDER,
                Constants.TYPE,
                Constants.TYPE_MAX_RESULTS_VIDEOS,
                pageToken,
            )
            Log.e("RESPONSE", response.toString());
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        result.onSuccess(response.body())
                    } else {
                        result.onFailure(context.resources.getString(R.string.connection_error))
                    }
                } catch (e: HttpException) {
                    result.onException(e)
                }
            }
        }
    }

}