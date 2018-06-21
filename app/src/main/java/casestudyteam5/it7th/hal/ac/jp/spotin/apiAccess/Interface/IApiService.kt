package casestudyteam5.it7th.hal.ac.jp.spotin.apiAccess.Interface

import casestudyteam5.it7th.hal.ac.jp.spotin.apiAccess.Entity.SpotData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface ApiService {
  @GET("")
  fun myself(): Call<SpotData>
}
fun createService(url: String): ApiService {
  //Replace the space name with your own space name
  //https://myspacename.backlogtool.com/
  //https://myspacename.backlog.jp/
  val BASE_URL = url

  val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

  return retrofit.create(ApiService::class.java)
}
