package com.eggman.circleciandroid.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.eggman.circleciandroid.CircleApplication
import com.eggman.circleciandroid.R
import com.eggman.circleciandroid.model.Project
import com.eggman.circleciandroid.service.CircleApi
import com.eggman.circleciandroid.session.Session
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/4/16
 */
class StartActivity : AppCompatActivity(){

    @Inject
    lateinit var session:Session
    @Inject
    lateinit var circleApi:CircleApi

    lateinit var etHome: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CircleApplication.graph.inject(this)

        setContentView(R.layout.activity_home)

        etHome = findViewById(R.id.act_home_et_welcome) as TextView

        etHome.setText("Welcome " + session.getUser()?.name)

        circleApi.getProjects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onProjectsLoaded(it) })
    }

    private fun onProjectsLoaded(projects:List<Project>) {
        Toast.makeText(this, "projects loaded, count= " + projects.size, Toast.LENGTH_LONG).show()
        Toast.makeText(this, "branches loaded, count= " + projects[0].branches.size, Toast.LENGTH_LONG).show()
    }
}