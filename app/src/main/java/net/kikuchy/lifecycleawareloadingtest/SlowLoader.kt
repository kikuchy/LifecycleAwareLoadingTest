package net.kikuchy.lifecycleawareloadingtest

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by hiroshi.kikuchi on 2017/09/08.
 */
class SlowLoader(context: Context): AsyncTaskLoader<String>(context) {

    // 結果をメモリに抱えておく
    var loaded: String? = null

    override fun onStartLoading() {
        super.onStartLoading()
        forceLoad()
    }

    override fun loadInBackground(): String {
        // メモリに抱えた読み込み済みの結果があればそれを onLoadFinished に返す
        // 再読込させない
        loaded?.let {
            Log.d("LOADER", "using cache result")
            return it
        }

        Log.d("LOADER", "start loading")
        val single = SlowRepository().get()
        val result = single.blockingGet()
        Log.d("LOADER", "done loading")
        loaded = result
        return  loaded ?: throw NoSuchElementException("しんだ")
    }
}