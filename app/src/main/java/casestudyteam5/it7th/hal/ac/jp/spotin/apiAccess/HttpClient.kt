package casestudyteam5.it7th.hal.ac.jp.spotin.apiAccess

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

val httpBuilder: OkHttpClient.Builder get() {
    val httpClient = OkHttpClient.Builder().addInterceptor ( Interceptor { chain ->
      val original = chain.request()

      val request = original.newBuilder()
        .header("Accept", "application/json")
        .method(original.method(), original.body())
        .build()

      return@Interceptor chain.proceed(request)
    })
      .readTimeout(30, TimeUnit.SECONDS)
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    httpClient.addInterceptor(loggingInterceptor)

    return httpClient
}
