package com.eggman.circleciandroid.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
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

    lateinit var etHome:TextView
    lateinit var rvProjects:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CircleApplication.graph.inject(this)

        setContentView(R.layout.activity_home)

        etHome = findViewById(R.id.act_home_et_welcome) as TextView
        rvProjects = findViewById(R.id.act_home_rv_projects) as RecyclerView

        etHome.text = "Welcome " + session.getUser()?.name + ", select a project below."

        circleApi.getProjects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onProjectsLoaded(it) })
    }

    private fun onProjectsLoaded(projects:List<Project>) {
        rvProjects.layoutManager = LinearLayoutManager(this)
        rvProjects.adapter = ProjectListAdapter(projects)
    }
}