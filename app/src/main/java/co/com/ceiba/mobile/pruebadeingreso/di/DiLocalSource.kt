package co.com.ceiba.mobile.pruebadeingreso.di

import android.content.Context
import androidx.room.Room
import co.com.ceiba.mobile.pruebadeingreso.data.local.AppUserDatabase
import co.com.ceiba.mobile.pruebadeingreso.data.local.UserDao
import co.com.ceiba.mobile.pruebadeingreso.data.local.UserLocalSource
import co.com.ceiba.mobile.pruebadeingreso.data.local.UserLocalSourceImpl
import co.com.ceiba.mobile.pruebadeingreso.rest.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiLocalSource {

    @Provides
    @Singleton
    fun injectLocalSource (userDao: UserDao) : UserLocalSource = UserLocalSourceImpl(userDao)

    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext context : Context) =
        Room.databaseBuilder(context, AppUserDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideUserDao(appDatabase: AppUserDatabase): UserDao {
        return appDatabase.userDao()
    }

}