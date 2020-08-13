package com.maritime.githubsample.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.maritime.githubsample.MainApplication
import com.maritime.githubsample.repository.RepoRepository

class ViewModelFactory constructor(
    private val repoRepository: RepoRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(ReposViewModel::class.java) ->
                    ReposViewModel(repoRepository)
                isAssignableFrom(RepoDetailsViewModel::class.java) ->
                    RepoDetailsViewModel(repoRepository)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as MainApplication).repoRepository
    return ViewModelFactory(repository)
}