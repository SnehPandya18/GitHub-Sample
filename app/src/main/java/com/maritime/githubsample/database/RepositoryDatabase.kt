package com.maritime.githubsample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.maritime.githubsample.model.Repository

@Database(entities = [Repository::class], version = 1, exportSchema = false)
abstract class RepositoryDatabase : RoomDatabase() {

    abstract fun repoDao(): RepositoryDao

    companion object {
        @Volatile
        private var instance: RepositoryDatabase? = null
        fun getInstance(context: Context): RepositoryDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: Room.databaseBuilder(context, RepositoryDatabase::class.java, "repos.db")
                        .build()
            }
        }
    }

}