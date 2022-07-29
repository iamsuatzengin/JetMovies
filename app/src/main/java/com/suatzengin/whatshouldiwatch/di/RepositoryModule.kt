package com.suatzengin.whatshouldiwatch.di

import com.suatzengin.whatshouldiwatch.data.repository.LocalRepositoryImpl
import com.suatzengin.whatshouldiwatch.data.repository.NetworkRepositoryImpl
import com.suatzengin.whatshouldiwatch.domain.repository.LocalRepository
import com.suatzengin.whatshouldiwatch.domain.repository.NetworkRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(repositoryImpl: NetworkRepositoryImpl): NetworkRepository

    @Binds
    @Singleton
    abstract fun bindLocalRepository(localRepositoryImpl: LocalRepositoryImpl): LocalRepository

}