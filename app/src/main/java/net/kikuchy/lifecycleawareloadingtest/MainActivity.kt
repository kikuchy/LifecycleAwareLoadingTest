package net.kikuchy.lifecycleawareloadingtest

import android.arch.lifecycle.LifecycleActivity
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.trello.lifecycle2.android.lifecycle.AndroidLifecycle
import com.trello.rxlifecycle2.android.RxLifecycleAndroid

class MainActivity : LifecycleActivity() {

    lateinit var generator: SlowLoaderGenerator

    val rxLifecycleProvidor = AndroidLifecycle.createLifecycleProvider(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("LOADER", "activity onCreate")

        // 古き良きLoader
        generator = SlowLoaderGenerator(
                this,
                ResultApplyViewMediator(
                        findViewById<TextView>(R.id.loader_result)
                )
        )
        supportLoaderManager.initLoader(
                0,
                null,
                generator
        )


        val viewModel = ViewModelProviders.of(this).get(SlowViewModel::class.java)
        val vmMediator = ResultApplyViewMediator(
                findViewById<TextView>(R.id.view_model_result)
        )
        viewModel.publisher
                .compose(rxLifecycleProvidor.bindToLifecycle())
                .subscribe { value -> vmMediator.apply(value)
                }
        viewModel.startLoading()
    }
}
