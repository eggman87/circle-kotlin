package com.eggman.circleciandroid

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.eggman.circleciandroid.model.User
import com.eggman.circleciandroid.service.CircleApi
import com.eggman.circleciandroid.session.Session
import com.eggman.circleciandroid.ui.StartActivity
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

class LaunchActivity : AppCompatActivity() {

    @Inject
    lateinit var session:Session

    @Inject
    lateinit var circleApi:CircleApi

    private val etToken: EditText by lazy { findViewById(R.id.act_launch_et_circle_token) as EditText }
    private val btnLogin: Button by lazy { findViewById(R.id.act_launch_btn_authenticate) as Button }

    companion object {
        const val TAG = "LaunchActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CircleApplication.graph.inject(this)

        setContentView(R.layout.activity_launch)

        btnLogin.setOnClickListener({
            session.setCircleToken(etToken.text.toString())
            checkState()
        })

        if (savedInstanceState == null) {
            checkState()
        }
    }

    override fun onPause() {
        super.onPause()

        session.onLifecyclePause()
    }

    private fun checkState() {
        var circleToken = session.getCircleToken()
        if (circleToken != null) {
            tryToLoadUserAndAdvance()
        }
    }

    private fun tryToLoadUserAndAdvance() {
        circleApi.getUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onUserLoaded(it) }, { onUserLoadError(it) })
    }

    private fun onUserLoaded(user:User) {
        session.setUser(user)

        val homeIntent = Intent(this, StartActivity::class.java)
        startActivity(homeIntent)


    }

    private fun onUserLoadError(error:Throwable) {
        Log.e(TAG, "unable to load user", error)
    }
}
