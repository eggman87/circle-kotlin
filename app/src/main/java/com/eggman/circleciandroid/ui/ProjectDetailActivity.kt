package com.eggman.circleciandroid.ui

import android.os.Bundle
import com.eggman.circleciandroid.model.Project
import kotlinx.android.synthetic.main.activity_project_detail.*

/**
 * Created by mharris on 2/6/16.
 * EggmanProjects.
 */
class ProjectDetailActivity : BaseActivity() {

    lateinit var project: Project

    companion object {
        const val EXTRA_PROJECT = "extra_project"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        project = intent.extras.get(EXTRA_PROJECT) as Project

        tvTitle.text = project.reponame
    }
}