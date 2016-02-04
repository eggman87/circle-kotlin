package com.eggman.circleciandroid.injection

import com.eggman.circleciandroid.LaunchActivity
import com.eggman.circleciandroid.ui.StartActivity
import dagger.Component
import javax.inject.Singleton

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/4/16
 */

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {

    fun inject(act: StartActivity)
    fun inject(act: LaunchActivity)
}