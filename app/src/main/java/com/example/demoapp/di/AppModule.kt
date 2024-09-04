package com.example.demoapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.demoapp.data.local.HouseDao
import com.example.demoapp.data.local.HouseDatabase
import com.example.demoapp.data.remote.CarApiService
import com.example.demoapp.data.remote.HouseApiService
import com.example.demoapp.data.repository.CarRepositoryImpl
import com.example.demoapp.data.repository.HouseRepositoryImpl
import com.example.demoapp.domain.repository.CarRepository
import com.example.demoapp.domain.repository.HouseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @Named("HouseRetrofit")
    fun provideRetrofit(
        @Named("HouseClient") client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://zillow56.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named("HouseClient")
    fun provideOkHttpClient(
        interceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "x-rapidapi-key",
                        "55e1d169706msh8cf8573599679cap1e2de3jsna37408e4a66f"
                    )
                    .addHeader("x-rapidapi-host", "zillow56.p.rapidapi.com")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(10L, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideWeatherApi(@Named("HouseRetrofit") retrofit: Retrofit): HouseApiService {
        return retrofit.create(HouseApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): HouseDatabase {
        return Room.databaseBuilder(
            appContext,
            HouseDatabase::class.java,
            "houses"
        ).build()
    }

    @Provides
    @Singleton
    fun provideHouseDao(database: HouseDatabase): HouseDao {
        return database.houseDao()
    }

    @Provides
    @Singleton
    fun provideHouseRepository(
        apiService: HouseApiService,
        houseDao: HouseDao
    ): HouseRepository {
        return HouseRepositoryImpl(houseDao, apiService)
    }

    @Provides
    @Singleton
    @Named("CarRetrofit")
    fun provideCarRetrofit(
        @Named("CarClient") client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://car-specs.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    @Named("CarClient")
    fun provideCarOkHttpClient(
        interceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader(
                        "x-rapidapi-key",
                        "3e594b6565msh8ef2a36fdf0108dp113257jsn441a90df4099"
                    )
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(10L, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideCarApi(@Named("CarRetrofit") retrofit: Retrofit): CarApiService {
        return retrofit.create(CarApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideCarRepository(
        apiService: CarApiService
    ): CarRepository {
        return CarRepositoryImpl(apiService)
    }
}