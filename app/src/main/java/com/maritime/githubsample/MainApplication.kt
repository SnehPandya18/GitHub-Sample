package com.maritime.githubsample

import android.app.Application
import com.maritime.githubsample.repository.RepoRepository
import com.maritime.githubsample.repository.ServiceLocator

class MainApplication : Application() {

    val repoRepository: RepoRepository
        get() = ServiceLocator.provideReposRepository(this)

}