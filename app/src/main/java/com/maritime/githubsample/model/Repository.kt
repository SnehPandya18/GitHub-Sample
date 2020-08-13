package com.maritime.githubsample.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "repos")
data class Repository(
    @PrimaryKey
    val id: Long,
    val name: String,
    val description: String,
    val html_url: String,
    val stargazers_count: Int,
    val forks: Int,
    val pushed_at: String,
    val language: String,
    @Embedded
    val owner: Owner
)