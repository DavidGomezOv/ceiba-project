package co.com.ceiba.mobile.pruebadeingreso.di

import android.content.Context
import co.com.ceiba.mobile.pruebadeingreso.core.InternetChecker
import co.com.ceiba.mobile.pruebadeingreso.data.remote.UserApiSource
import co.com.ceiba.mobile.pruebadeingreso.domain.UserRepository
import co.com.ceiba.mobile.pruebadeingreso.domain.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiRepository {

    @Provides
    @Singleton
    fun injectRepository(userApiSource: UserApiSource,
                         @ApplicationContext context: Context,
                         internetChecker: InternetChecker
    ) : UserRepository = UserRepositoryImpl(userApiSource, context, internetChecker)

}