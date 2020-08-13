package com.maritime.githubsample.repository

import android.content.Context
import com.maritime.githubsample.api.GitHubAPIService
import com.maritime.githubsample.api.RepositoryAPIService
import com.maritime.githubsample.database.RepositoryDatabase
import com.maritime.githubsample.database.RepositoryRoomService

object ServiceLocator {

    private var database: RepositoryDatabase? = null
    private var githubApiService: GitHubAPIService? = null

    @Volatile
    var repoRepository: RepoRepository? = null

    fun provideReposRepository(context: Context): RepoRepository {
        synchronized(this) {
            return repoRepository ?: repoRepository ?: createReposRepository(context)
        }
    }

    private fun createReposRepository(context: Context): RepoRepository {
        val newRepository = RepoRepository.getInstance(
            createRepoAPI(),
            createRepoDB(context)
        )
        repoRepository = newRepository
        return newRepository
    }

    private fun createRepoAPI(): RepositoryAPIService {
        val apiService = githubApiService ?: createGithubApiService()
        return RepositoryAPIService(apiService)
    }

    private fun createGithubApiService(): GitHubAPIService {
        return githubApiService ?: GitHubAPIService.create()
    }

    private fun createRepoDB(context: Context): RepositoryRoomService {
        val database = database ?: createDatabase(context)
        return RepositoryRoomService(database.repoDao())
    }

    private fun createDatabase(context: Context): RepositoryDatabase {
        return database ?: RepositoryDatabase.getInstance(context)
    }
}