package com.maritime.githubsample.repository

import android.util.Log
import com.maritime.githubsample.api.RepositoryAPIService
import com.maritime.githubsample.database.RepositoryRoomService
import com.maritime.githubsample.model.Repository

class RepoRepository(
    private val repositoryAPI: RepositoryDataSource,
    private val repositoryDB: RepositoryDataSource
) {

    var cachedRepositories: List<Repository>? = null
    var cachedRepository: Repository? = null

    suspend fun getRepos(forceRefresh: Boolean = false, sort: String? = null, language: String? = null): List<Repository>? {
        // Return cached list
        if (!forceRefresh && cachedRepositories != null) return cachedRepositories

        // Return database list
        if (!forceRefresh && cachedRepositories == null) {
            val dbRepos = repositoryDB.getRepo("java", "weekly")
            if (!dbRepos.isNullOrEmpty()) {
                cachedRepositories = dbRepos
                return cachedRepositories
            }
        }

        try {
            cachedRepositories = repositoryAPI.getRepo("java", "weekly")
            cachedRepositories?.let { repos ->
                repositoryDB.deleteAll()
                for (repo in repos) repositoryDB.insert(repo)
            }
        } catch (ex: Exception) {
            Log.e("TAG_LOG", ex.toString())
        }
        return cachedRepositories
    }

    suspend fun getRepo(id: Long): Repository? {
        // Return cached repo
        if (cachedRepository != null && cachedRepository?.id == id) return cachedRepository

        // Find cached repo and return it
        val repository: Repository? = cachedRepositories?.find { it.id == id }
        if (repository != null) {
            cachedRepository = repository
            return repository
        }

        // Return repo in DB
        val dbRepo = repositoryDB.getRepos(id)
        if (dbRepo != null) {
            cachedRepository = dbRepo
            return dbRepo
        }

        // Remote data source required
        try {
            cachedRepository = repositoryAPI.getRepos(id)
        } catch (ex: Exception) {
            Log.e("TAG_LOG", ex.toString())
        }
        return cachedRepository
    }

    companion object {
        @Volatile private var INSTANCE: RepoRepository? = null
        fun getInstance(repositoryApiService: RepositoryAPIService, repositoryDB: RepositoryRoomService): RepoRepository {
            return INSTANCE ?: RepoRepository(repositoryApiService, repositoryDB).apply { INSTANCE = this }
        }
    }
}