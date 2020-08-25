package com.sahla.sahla.di

import android.content.Context
import androidx.room.Room
import com.sahla.sahla.data.manager.db.SahlaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): SahlaDatabase =
        Room.databaseBuilder(context, SahlaDatabase::class.java, "sahla.db")
            .fallbackToDestructiveMigration().build()

}