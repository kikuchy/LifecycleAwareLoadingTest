package net.kikuchy.lifecycleawareloadingtest

import android.widget.TextView

/**
 * Created by hiroshi.kikuchi on 2017/09/08.
 */
class ResultApplyViewMediator(val resultView: TextView) {
    fun apply(text: String) {
        resultView.text = text
    }
}