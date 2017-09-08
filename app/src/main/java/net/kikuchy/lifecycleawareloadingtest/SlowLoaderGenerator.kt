package net.kikuchy.lifecycleawareloadingtest

import android.content.Context
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader

/**
 * Created by hiroshi.kikuchi on 2017/09/08.
 */
class SlowLoaderGenerator(val context: Context, val viewMediator: ResultApplyViewMediator): LoaderManager.LoaderCallbacks<String> {
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<String> {
        return SlowLoader(context)
    }

    override fun onLoadFinished(loader: Loader<String>?, data: String) {
        // Activity/Fragment が画面にいる && LoaderのloadInBackgroundがreturnされたときに呼ばれる
        // loadInBackground はActivityが画面に帰ってきたときにも呼ばれる。
        // すなわち、結果が出た後、Activityが画面にいればすぐ呼ばれるし、居なければ戻ってくるたびに呼ばれる
        // 作用先の状態に依存する処理を書くと画面に戻ってくるたびに結果が変わって危険なので、冪等性のある処理にするべき
        viewMediator.apply(data)
    }

    override fun onLoaderReset(loader: Loader<String>?) {
        viewMediator.apply("Waiting")
    }
}