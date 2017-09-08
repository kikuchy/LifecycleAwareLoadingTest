package net.kikuchy.lifecycleawareloadingtest

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

/**
 * Created by hiroshi.kikuchi on 2017/09/08.
 */
class SlowViewModel: ViewModel() {
    private val subject = BehaviorSubject.create<String>()

    val publisher: Observable<String>
        get() = subject
    val current: String
        get() = subject.value

    fun startLoading() {
        val single = SlowRepository().get()
        single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ value ->
            subject.onNext(value)
        }, { e ->
            subject.onError(e)
        })
    }
}