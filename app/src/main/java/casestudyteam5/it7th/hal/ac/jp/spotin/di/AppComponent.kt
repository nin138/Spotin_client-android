package casestudyteam5.it7th.hal.ac.jp.spotin.di

import casestudyteam5.it7th.hal.ac.jp.spotin.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Component(modules = [
  AndroidSupportInjectionModule::class,
  ActivityModule::class,
  NetworkModule::class
])
@Singleton
interface AppComponent : AndroidInjector<App> {

  @Component.Builder
  abstract class Builder : AndroidInjector.Builder<App>()

  fun retrofit(): Retrofit

  fun okHttpClient(): OkHttpClient
}
