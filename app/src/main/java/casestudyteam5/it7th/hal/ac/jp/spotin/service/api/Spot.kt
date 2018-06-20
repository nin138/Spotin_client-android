package casestudyteam5.it7th.hal.ac.jp.spotin.service.api

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request

class SpotApi {
  fun getSpotList(category: String, lat: String, lng: String): Spots {
    val url = """$BASE_URL/spot/list/$category/$lat,$lng"""
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()
    val res = client.newCall(request).execute()
    return convertJsonToSpots(res.body()!!.string())
  }
  private fun convertJsonToSpots(json: String): Spots {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(Spots::class.java)
    return adapter.fromJson(json) ?: Spots(spot = arrayOf())
  }

  companion object {
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
    const val BASE_URL = "https://5z59rcfzu9.execute-api.ap-northeast-1.amazonaws.com/spotin_dev"
  }
}