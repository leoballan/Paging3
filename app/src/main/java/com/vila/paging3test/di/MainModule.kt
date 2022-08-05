package com.vila.paging3test.di

import com.vila.paging3test.data.RandomUserApi
import com.vila.paging3test.data.repositories.Repository
import com.vila.paging3test.ui.MainViewModel
import com.vila.randomusertest.util.Constant
import com.vila.randomusertest.util.Constant.URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    fun provideRandomUsersApi(): RandomUserApi {
        return Retrofit
            .Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomUserApi::class.java)
    }


}