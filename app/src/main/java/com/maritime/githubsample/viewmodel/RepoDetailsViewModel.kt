package com.maritime.githubsample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maritime.githubsample.model.Repository
import com.maritime.githubsample.repository.RepoRepository
import kotlinx.coroutines.launch

class RepoDetailsViewModel(private val repoRepository: RepoRepository) : ViewModel() {

    private val _repo = MutableLiveData<Repository>()
    val repository: LiveData<Repository> = _repo

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun fetchRepo(id: Long) {
        viewModelScope.launch {
            val result = repoRepository.getRepo(id)
            if (result != null) {
                _repo.postValue(result)
            } else {
                _error.postValue("Could not get repository!")
            }
        }
    }
}