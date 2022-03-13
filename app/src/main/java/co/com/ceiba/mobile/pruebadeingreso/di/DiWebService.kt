package co.com.ceiba.mobile.pruebadeingreso.di

import co.com.ceiba.mobile.pruebadeingreso.domain.WebService
import co.com.ceiba.mobile.pruebadeingreso.rest.Constants
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DiWebService {

    @Singleton
    @Provides
    fun injectWebService(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.URL_BASE)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitWebService(retrofit: Retrofit):WebService{
        return retrofit.create(WebService::class.java)
    }

}