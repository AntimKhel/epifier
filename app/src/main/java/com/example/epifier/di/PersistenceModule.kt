package com.example.epifier.di

import android.content.Context
import androidx.room.Room
import com.example.epifier.repository.local.AppDatabase
import com.example.epifier.repository.local.DetailDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext application: Context): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, "detail-database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDao(appDatabase: AppDatabase): DetailDao {
        return appDatabase.detailDao()
    }
}