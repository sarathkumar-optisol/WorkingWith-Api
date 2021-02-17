package com.example.workingwithapi.di

import com.example.workingwithapi.data.api.LoginApi
import com.example.workingwithapi.main.DefaultMainRepository
import com.example.workingwithapi.main.MainRepository
import com.example.workingwithapi.others.Constants.BASE_URL
import com.example.workingwithapi.util.DispatcherProvider
import dagger.Module
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.Provides
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideLoginApi() : LoginApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(LoginApi::class.java)


    @Singleton
    @Provides
    fun provideMainRepository(api: LoginApi) : MainRepository = DefaultMainRepository(api)

    @Singleton
    @Provides
    fun provideDispatchers() : DispatcherProvider = object : DispatcherProvider{
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }


}