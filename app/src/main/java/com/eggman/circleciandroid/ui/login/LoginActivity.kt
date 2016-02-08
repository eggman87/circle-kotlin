package com.eggman.circleciandroid.ui.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import com.eggman.circleciandroid.CircleApplication
import com.eggman.circleciandroid.R
import com.eggman.circleciandroid.model.User
import com.eggman.circleciandroid.service.CircleApi
import com.eggman.circleciandroid.session.Session
import com.eggman.circleciandroid.ui.BaseActivity
import com.eggman.circleciandroid.ui.ProjectListActivity
import kotlinx.android.synthetic.main.activity_login.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/8/16
 */
class LoginActivity : BaseActivity() {

    @Inject
    lateinit var session: Session
    @Inject
    lateinit var circleApi: CircleApi

    companion object {
        const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CircleApplication.graph.inject(this)

        setContentView(R.layout.activity_login)

        btnAuthenticate.setOnClickListener({
            session.setCircleToken(etCircleToken.text.toString())
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

    private fun onUserLoaded(user: User) {
        session.setUser(user)

        val homeIntent = Intent(this, ProjectListActivity::class.java)
        startActivity(homeIntent)

        finish()
    }

    private fun onUserLoadError(error:Throwable) {
        Log.e(LoginActivity.TAG, "unable to load user", error)

        Snackbar.make(etCircleToken, R.string.login_error, Snackbar.LENGTH_LONG).show()

    }

}