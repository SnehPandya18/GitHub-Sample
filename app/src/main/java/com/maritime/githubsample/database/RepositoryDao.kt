package com.maritime.githubsample.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.maritime.githubsample.model.Repository

@Dao
interface RepositoryDao {

    @Query("SELECT * FROM repos ORDER BY stargazers_count DESC")
    suspend fun getRepos(): List<Repository>?

    @Query("SELECT * FROM repos WHERE id == :repoId")
    suspend fun getRepo(repoId: Long): Repository?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repository: Repository)

    @Query("DELETE FROM repos")
    suspend fun deleteAll()

}