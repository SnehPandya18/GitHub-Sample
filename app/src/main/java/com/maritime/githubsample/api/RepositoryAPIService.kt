package com.maritime.githubsample.api

import com.maritime.githubsample.model.Repository
import com.maritime.githubsample.repository.RepositoryDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryAPIService internal constructor(
    private val ApiService: GitHubAPIService,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RepositoryDataSource {

    override suspend fun getRepo(language: String, since: String): List<Repository>? =
        withContext(ioDispatcher) { ApiService.getDevelopers(language, since)?.items }

    override suspend fun getRepos(id: Long): Repository? {
        TODO("Not yet implemented")
    }

    override suspend fun insert(repository: Repository) = TODO("Not implemented")

    override suspend fun deleteAll() = TODO("Not implemented")

}