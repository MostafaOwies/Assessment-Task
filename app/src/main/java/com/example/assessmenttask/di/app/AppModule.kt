package com.example.assessmenttask.di.app

import android.app.Application
import android.content.Context
import com.example.assessmenttask.di.RetrofitQ
import com.example.assessmenttask.domain.UrlProvider
import com.example.assessmenttask.domain.user.UserAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @AppScope
    @RetrofitQ
    fun provideRetrofit(urlProvider: UrlProvider) :Retrofit {
        return  Retrofit.Builder()
            .baseUrl(urlProvider.getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

   /* @Provides
    @AppScope
    fun provideDataBase(@ApplicationContext context:Context):PrayerDB =
        Room.databaseBuilder(
            context,
            PrayerDB::class.java , DATABASE_NAME
        ).fallbackToDestructiveMigration().build()


    @Provides
    @AppScope
    fun provideActionDao(db: PrayerDB): PrayersDao {
        return db.getPrayersDao()
    }*/

    @Provides
    @AppScope
    fun urlProvider()=UrlProvider()

    @Provides
    @AppScope
    fun userAPI(@RetrofitQ retrofit: Retrofit) =retrofit.create(UserAPI::class.java)


    @Provides
    @AppScope
    fun provideApplicationContext(application: Application): Context {
        return application.applicationContext
    }

}