package com.eggman.circleciandroid.ui.project

import android.os.Bundle
import com.eggman.circleciandroid.R
import com.eggman.circleciandroid.model.Project
import com.eggman.circleciandroid.ui.BaseActivity
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

        setContentView(R.layout.activity_project_detail)

        project = intent.extras.get(EXTRA_PROJECT) as Project

        title = project.reponame
        tvLanguage.text = project.language
        tvVcsUrl.text = project.vcsUrl
    }
}