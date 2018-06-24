package casestudyteam5.it7th.hal.ac.jp.spotin.apiAccess

import casestudyteam5.it7th.hal.ac.jp.spotin.apiAccess.Entity.JsonData
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlin.jvm.java

class ApiService{
  fun getDataList(json:String, dataClass:JsonData): JsonData {
    val moshi = Moshi.Builder().build()
    val adapter = moshi.adapter(dataClass::class.java)
    val jsonDataList = adapter.fromJson(json)
    return jsonDataList!!
  }
  fun getJsonString(url : String): String {
    val request = Request.Builder().url(url).build()
    val response = OkHttpClient().newCall(request).execute()
    return response.body()!!.string()
  }
}
