package co.com.ceiba.mobile.pruebadeingreso.di

import co.com.ceiba.mobile.pruebadeingreso.core.InternetChecker
import co.com.ceiba.mobile.pruebadeingreso.data.local.UserLocalSource
import co.com.ceiba.mobile.pruebadeingreso.data.remote.UserApiSource
import co.com.ceiba.mobile.pruebadeingreso.domain.UserRepository
import co.com.ceiba.mobile.pruebadeingreso.domain.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiRepository {

    @Provides
    @Singleton
    fun injectRepository(userApiSource: UserApiSource,
                         userLocalSource: UserLocalSource,
                         internetChecker: InternetChecker,
    ) : UserRepository = UserRepositoryImpl(userApiSource, userLocalSource, internetChecker)

}