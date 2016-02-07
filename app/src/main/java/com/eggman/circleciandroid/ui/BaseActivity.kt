package com.eggman.circleciandroid.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.eggman.circleciandroid.CircleApplication
import com.squareup.otto.Bus
import javax.inject.Inject

/**
 * Created by mharris on 2/6/16.
 * EggmanProjects.
 */
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var bus:Bus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CircleApplication.graph.inject(this)

        bus.register(this)
    }

    override fun onPause() {
        super.onPause()

        bus.unregister(this)
    }

}