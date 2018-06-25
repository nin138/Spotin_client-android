package casestudyteam5.it7th.hal.ac.jp.spotin.di

import casestudyteam5.it7th.hal.ac.jp.spotin.BuildConfig
import casestudyteam5.it7th.hal.ac.jp.spotin.service.api.SpotApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

  @Provides
  @Singleton
  fun provideConverter(): MoshiConverterFactory = MoshiConverterFactory.create()

  @Provides
  @Singleton
  fun provideOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
      .connectTimeout(15, TimeUnit.SECONDS)
      .readTimeout(15, TimeUnit.SECONDS)
      .writeTimeout(15, TimeUnit.SECONDS)
      .apply {
        if (BuildConfig.DEBUG) {
          addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
      }.build()

  @Provides
  @Singleton
  fun provideRetrofit(client: OkHttpClient): Retrofit =
    Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(provideConverter())
      .addCallAdapterFactory(CoroutineCallAdapterFactory())
      .client(client)
      .build()

  @Provides
  @Singleton
  fun provideSpotApi(retrofit: Retrofit): SpotApi.Service = retrofit.create(SpotApi.Service::class.java)

  companion object {
    const val BASE_URL = "https://5z59rcfzu9.execute-api.ap-northeast-1.amazonaws.com/spotin_dev/"
  }
}
