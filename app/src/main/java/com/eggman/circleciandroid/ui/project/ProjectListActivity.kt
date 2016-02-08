package com.eggman.circleciandroid.ui.project

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.eggman.circleciandroid.CircleApplication
import com.eggman.circleciandroid.R
import com.eggman.circleciandroid.model.Project
import com.eggman.circleciandroid.service.CircleApi
import com.eggman.circleciandroid.session.Session
import com.eggman.circleciandroid.ui.BaseActivity
import com.eggman.circleciandroid.ui.event.ProjectClickedEvent
import com.eggman.circleciandroid.ui.login.LoginActivity
import com.squareup.otto.Subscribe
import kotlinx.android.synthetic.main.activity_project_list.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

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

    companion object {
        val ACTION_LOGOUT = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        CircleApplication.graph.inject(this)

        setContentView(R.layout.activity_project_list)

        title = session.getUser()?.name

        circleApi.getProjects()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ onProjectsLoaded(it) })
    }

    override fun onPause() {
        super.onPause()

        session.onLifecyclePause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menu.add(0, ACTION_LOGOUT, 0, R.string.login_action_logout)
                .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_NEVER)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == ACTION_LOGOUT) {
            session.setCircleToken(null)

            val loginIntent = Intent(this, LoginActivity::class.java)
            startActivity(loginIntent)
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun onProjectsLoaded(projects:List<Project>) {
        rvProjects.layoutManager = LinearLayoutManager(this)
        rvProjects.adapter = ProjectListAdapter(projects, bus)
    }

    @Subscribe
    @Suppress("unused")
    fun onProjectClicked(event: ProjectClickedEvent) {
        val intent = Intent(this, ProjectDetailActivity::class.java)
        intent.putExtra(ProjectDetailActivity.EXTRA_PROJECT, event.project)
        startActivity(intent)
    }
}