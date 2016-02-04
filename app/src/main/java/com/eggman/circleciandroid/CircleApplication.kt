package com.eggman.circleciandroid

import android.app.Application
import com.eggman.circleciandroid.injection.AppComponent
import com.eggman.circleciandroid.injection.AppModule
import com.eggman.circleciandroid.injection.DaggerAppComponent

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/4/16
 */
class CircleApplication : Application() {

    companion object  {
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        graph = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}