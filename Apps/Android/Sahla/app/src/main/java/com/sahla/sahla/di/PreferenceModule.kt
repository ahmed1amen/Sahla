package com.sahla.sahla.di

import android.content.Context
import android.content.SharedPreferences
import com.sahla.sahla.data.local.PreferenceHelper
import com.sahla.sahla.data.local.PreferenceUtils
import com.sahla.sahla.data.manager.preference.LocaleManager
import com.sahla.sahla.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object PreferenceModule {

    @Singleton
    @Provides
    fun sharedPreference(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(Constants.SAHLA_PREFERENCE, Context.MODE_PRIVATE)

    @Singleton
    @Provides
    fun providePreferenceUtils(sharedPreferences: SharedPreferences): PreferenceUtils =
        PreferenceUtils(sharedPreferences)

    @Singleton
    @Provides
    fun providePreferenceHelper(
        @ApplicationContext context: Context,
        preferenceUtils: PreferenceUtils
    ): PreferenceHelper = PreferenceHelper(context, preferenceUtils)

    @Singleton
    @Provides
    fun provideLocaleManager(preferenceHelper: PreferenceHelper): LocaleManager =
        LocaleManager(preferenceHelper)
}