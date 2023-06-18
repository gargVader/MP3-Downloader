package com.example.mp3downloader.di

import com.example.mp3downloader.data.model.VideoInfo
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesJsonAdapter(): JsonAdapter<VideoInfo> {
        return Moshi.Builder().build().adapter(VideoInfo::class.java)
    }

}