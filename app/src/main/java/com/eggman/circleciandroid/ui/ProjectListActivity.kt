package com.eggman.circleciandroid.ui

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.eggman.circleciandroid.CircleApplication
import com.eggman.circleciandroid.R
import com.eggman.circleciandroid.model.Project
import com.eggman.circleciandroid.service.CircleApi
import com.eggman.circleciandroid.session.Session
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

import kotlinx.android.synthetic.main.activity_home.*

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/4/16
 */
class ProjectListActivity : BaseActivity(){

    @Inject
    lateinit var session: Session
    @Inject
    lateinit var circleApi: CircleApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CircleApplication.graph.inject(this)

        setContentView(R.layout.activity_home)

        etWelcome.text = "Welcome " + session.getUser()?.name + ", select a project below."

        circleApi.getProjects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onProjectsLoaded(it) })
    }

    override fun onPause() {
        super.onPause()

        session.onLifecyclePause()
    }

    private fun onProjectsLoaded(projects:List<Project>) {
        rvProjects.layoutManager = LinearLayoutManager(this)
        rvProjects.adapter = ProjectListAdapter(projects)
    }
}