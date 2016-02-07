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

    var isRegistered:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CircleApplication.graph.inject(this)

        registerBus()
    }

    override fun onResume() {
        super.onResume()

        registerBus()
    }

    override fun onPause() {
        super.onPause()

        if (isRegistered) {
            bus.unregister(this)
            isRegistered = false
        }
    }

    fun registerBus() {
        if (!isRegistered) {
            bus.register(this)
            isRegistered = true
        }
    }

}