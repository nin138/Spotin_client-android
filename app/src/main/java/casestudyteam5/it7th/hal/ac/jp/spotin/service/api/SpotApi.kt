package casestudyteam5.it7th.hal.ac.jp.spotin.service.api

import casestudyteam5.it7th.hal.ac.jp.spotin.service.api.entity.SpotDetail
import casestudyteam5.it7th.hal.ac.jp.spotin.service.api.entity.Spots
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.withContext
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Inject

class SpotApi @Inject constructor(private val service: Service) {
  interface Service {
    @GET("spot/list/{category}/{lat},{lng}/{radius}")
    fun getSpotList(
      @Path("category")category: String,
      @Path("lat")lat: Double,
      @Path("lng")lng: Double,
      @Path("radius")radius: Int
    ): Deferred<Spots>

    @GET("spot/detail/{place_id}")
    fun getSpotDetail(@Path("place_id")placeId: String): Deferred<SpotDetail>
  }

  suspend fun getSpotList(category: String, lat: Double, lng: Double, radius: Int): Spots = withContext(CommonPool) {
    service.getSpotList(category, lat, lng, radius).await()
  }

  suspend fun getSpotDetail(spotId: String): SpotDetail = withContext(CommonPool) {
    service.getSpotDetail(spotId).await()
  }
}
