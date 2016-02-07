package com.eggman.circleciandroid.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eggman.circleciandroid.R
import com.eggman.circleciandroid.model.Project
import com.eggman.circleciandroid.ui.event.ProjectClickedEvent
import com.squareup.otto.Bus

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/5/16
 */
class ProjectListAdapter(val projects:List<Project>, val bus:Bus): RecyclerView.Adapter<ProjectListAdapter.ProjectViewHolder>() {

    override fun onBindViewHolder(holder: ProjectViewHolder?, position: Int) {
        holder?.bindProject(projects[position])

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ProjectViewHolder? {
        val inflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.list_item_project, parent, false)
        return ProjectViewHolder(view, bus)
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    class ProjectViewHolder(itemView: View, val bus:Bus) : RecyclerView.ViewHolder(itemView) {

        lateinit var tvTitle:TextView
        lateinit var tvAuthors:TextView

        init {
            tvTitle = itemView.findViewById(R.id.list_item_project_tv_title) as TextView
            tvAuthors = itemView.findViewById(R.id.list_item_project_tv_authors) as TextView
        }

        fun bindProject(project:Project) {
            tvTitle.text = project.reponame
            tvAuthors.text = project.username

            itemView.setOnClickListener {
                bus.post(ProjectClickedEvent(project))
            }
        }
    }
}