package net.kikuchy.lifecycleawareloadingtest

import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by hiroshi.kikuchi on 2017/09/08.
 */
class SlowRepository {
    fun get(): Single<String> {
        return Single.create { emitter ->
            val http = OkHttpClient()
            val request = Request.Builder()
                    .get()
                    .url("http://slowwly.robertomurray.co.uk/delay/7000/url/https://jsonplaceholder.typicode.com/posts")
                    .build()
            val call = http.newCall(request)
            if (!emitter.isDisposed) {
                val response = call.execute()
                if (response.isSuccessful) {
                    emitter.onSuccess(response.body()?.string()!!)
                } else {
                    emitter.onError(Exception("Error code: ${response.code()}"))
                }
            }
        }
    }
}