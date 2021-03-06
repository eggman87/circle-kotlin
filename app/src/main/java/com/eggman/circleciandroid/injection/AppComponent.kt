package com.eggman.circleciandroid.injection

import com.eggman.circleciandroid.ui.LaunchActivity
import com.eggman.circleciandroid.ui.BaseActivity
import com.eggman.circleciandroid.ui.project.ProjectListActivity
import com.eggman.circleciandroid.ui.login.LoginActivity
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

    fun inject(act: BaseActivity)
    fun inject(act: ProjectListActivity)
    fun inject(act: LaunchActivity)
    fun inject(act: LoginActivity)
}