package casestudyteam5.it7th.hal.ac.jp.spotin.service.api

import com.squareup.moshi.Moshi
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class SpotApi {
  suspend fun getSpotList(category: String, lat: String, lng: String): SpotApi.Spots {
    return withContext(DefaultDispatcher) {
      val url = """$BASE_URL/spot/list/$category/$lat,$lng"""
      val client = OkHttpClient()
      val request = Request.Builder().url(url).build()
      val res = client.newCall(request).execute()
      convertJsonToSpots(res.body()!!.string())
    }
  }
  private fun convertJsonToSpots(json: String): Spots {
    return Moshi.Builder().build().adapter(Spots::class.java)
      .fromJson(json) ?: Spots(spot = arrayOf())
  }
  companion object {
    const val BASE_URL = "https://5z59rcfzu9.execute-api.ap-northeast-1.amazonaws.com/spotin_dev"
  }
  data class Spot(
    val lat: String,
    val lng: String,
    val place_id: String,
    val name: String,
    val icon: String
  )

  data class Spots(
    //todo override equals and hashCode
    val spot: Array<Spot>
  )
}
