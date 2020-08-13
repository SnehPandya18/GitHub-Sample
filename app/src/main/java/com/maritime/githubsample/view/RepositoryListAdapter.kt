package com.maritime.githubsample.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maritime.githubsample.R
import com.maritime.githubsample.model.Repository

class RepositoryListAdapter(
    private var repositories: List<Repository>,
    private val clickListener: (Repository) -> Unit
) : RecyclerView.Adapter<RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.repo_item,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repositories.get(position)
        holder.bindData(repo, clickListener)
    }

    override fun getItemCount(): Int = repositories.size

    fun replaceData(repositories: List<Repository>) {
        this.repositories = repositories
        notifyDataSetChanged()
    }
}