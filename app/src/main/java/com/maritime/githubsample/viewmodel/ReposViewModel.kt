package com.maritime.githubsample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maritime.githubsample.model.Repository
import com.maritime.githubsample.repository.RepoRepository
import kotlinx.coroutines.launch

class ReposViewModel(private val repoRepository: RepoRepository) : ViewModel() {

    private val _repos = MutableLiveData<List<Repository>>()
    val repos: LiveData<List<Repository>> = _repos

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchRepos(forceRefresh: Boolean) {
        viewModelScope.launch {
            val result = repoRepository.getRepos(forceRefresh, "java")
            if (result != null) {
                _repos.postValue(result)
            } else {
                _error.postValue("Could not get repositories!")
            }
        }
    }
}