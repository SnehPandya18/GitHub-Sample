package com.maritime.githubsample.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.maritime.githubsample.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.content_frame, RepositoryListFragment()).commit()
    }

    override fun onBackPressed() {
        when (FRAGMENT_TAG) {
            "ReposFragment" -> super.onBackPressed()
            "RepoDetailsFragment" ->
                supportFragmentManager.beginTransaction().replace(R.id.content_frame, RepositoryListFragment()).commit()
        }
    }
    companion object {
        var FRAGMENT_TAG = "ReposFragment"
    }
}