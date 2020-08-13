package com.maritime.githubsample.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.maritime.githubsample.model.Repository
import kotlinx.android.synthetic.main.repo_item.view.*

class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var repository: Repository

    fun bindData(repository: Repository, clickListener: (Repository) -> Unit) {
        this.repository = repository
        itemView.setOnClickListener { clickListener(repository) }
        itemView.title.text = repository.name
        itemView.name.text = repository.description
    }
}