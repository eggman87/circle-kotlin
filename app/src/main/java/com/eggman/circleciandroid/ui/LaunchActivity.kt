package com.eggman.circleciandroid.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.eggman.circleciandroid.CircleApplication
import com.eggman.circleciandroid.R
import com.eggman.circleciandroid.model.User
import com.eggman.circleciandroid.service.CircleApi
import com.eggman.circleciandroid.session.Session
import com.eggman.circleciandroid.ui.project.ProjectListActivity
import com.eggman.circleciandroid.ui.login.LoginActivity
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class LaunchActivity : AppCompatActivity() {

    @Inject
    lateinit var session: Session
    @Inject
    lateinit var circleApi: CircleApi

    companion object {
        const val TAG = "LaunchActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CircleApplication.graph.inject(this)

        setContentView(R.layout.activity_launch)

        Handler().postDelayed({ checkState() }, 750)
    }

    override fun onPause() {
        super.onPause()

        session.onLifecyclePause()
    }

    private fun checkState() {
        var circleToken = session.getCircleToken()
        if (circleToken != null) {
            tryToLoadUserAndAdvance()
        } else {
            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
        }
    }

    private fun tryToLoadUserAndAdvance() {
        circleApi.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onUserLoaded(it) }, { onUserLoadError(it) })
    }

    private fun onUserLoaded(user: User) {
        session.setUser(user)

        val homeIntent = Intent(this, ProjectListActivity::class.java)
        startActivity(homeIntent)
    }

    private fun onUserLoadError(error:Throwable) {
        Log.e(TAG, "unable to load user", error)
    }
}
