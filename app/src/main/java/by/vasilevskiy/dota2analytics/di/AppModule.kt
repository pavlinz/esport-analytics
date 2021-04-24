package by.vasilevskiy.dota2analytics.di

import android.content.Context
import by.vasilevskiy.dota2analytics.Application
import by.vasilevskiy.dota2analytics.helpers.ConnectionLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): Application {
        return app as Application
    }

    @Provides
    fun provideConnectionLiveData(@ApplicationContext context: Context): ConnectionLiveData =
        ConnectionLiveData(context)
}