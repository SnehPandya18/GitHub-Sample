package com.maritime.githubsample.database

import com.maritime.githubsample.model.Repository
import com.maritime.githubsample.repository.RepositoryDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryRoomService internal constructor(
    private val repoDao: RepositoryDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : RepositoryDataSource {

    override suspend fun getRepo(language: String, since: String): List<Repository>?  =
        withContext(ioDispatcher) { repoDao.getRepos() }

    override suspend fun getRepos(id: Long): Repository? =
        withContext(ioDispatcher) { repoDao.getRepo(id) }

    override suspend fun insert(repository: Repository) =
        withContext(ioDispatcher) { repoDao.insert(repository) }

    override suspend fun deleteAll() =
        withContext(ioDispatcher) { repoDao.deleteAll() }

}