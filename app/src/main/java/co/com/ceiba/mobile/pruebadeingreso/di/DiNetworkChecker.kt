package co.com.ceiba.mobile.pruebadeingreso.di

import co.com.ceiba.mobile.pruebadeingreso.core.InternetChecker
import co.com.ceiba.mobile.pruebadeingreso.core.InternetCheckerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiNetworkChecker {

    @Singleton
    @Provides
    fun injectNetworkChecker() : InternetChecker = InternetCheckerImpl()

}