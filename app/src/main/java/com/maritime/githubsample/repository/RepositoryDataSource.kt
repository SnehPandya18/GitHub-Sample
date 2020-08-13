package com.maritime.githubsample.repository

import com.maritime.githubsample.model.Repository

interface RepositoryDataSource {

    suspend fun getRepo(language: String, since: String): List<Repository>?

    suspend fun getRepos(id: Long): Repository?

    suspend fun insert(repository: Repository)

    suspend fun deleteAll()

}