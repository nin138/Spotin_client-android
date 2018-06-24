package casestudyteam5.it7th.hal.ac.jp.spotin.addrecord

import android.content.Context
import android.net.Uri
import casestudyteam5.it7th.hal.ac.jp.spotin.data.TravelRecord
import java.util.Date

interface AddRecordContract {

  interface View {
    val isUpdate: Boolean
    fun takeImage() //画像の追加
    fun showImageList(imagepassList: List<TravelRecord.SpotImage>)
    fun editComment()
    fun selfcheckPermmision()
    fun showEmpryError()
    fun cancel()
    fun decision()
  }
  interface Presenter {
    fun saveRecord(place_id: String, comment: String, place_name: String, imagepassList: List<TravelRecord.SpotImage>?)
    fun getcameraUri(context: Context): Uri
    fun editImageList(imagepass: TravelRecord.SpotImage): List<TravelRecord.SpotImage>//表示用
    fun createImageSpot(place_id: String, imagepass: Uri?): TravelRecord.SpotImage
    fun createTravelRecord(place_id: String, comment: String, place_name: String, date: Date)
    fun createImageRecord(place_id: String, imagepassList: List<TravelRecord.SpotImage>)
    fun updataTravelRecord(place_id: String, comment: String, place_name: String, date: Date)
    fun updataImageRecord(place_id: String, imagepassList: List<TravelRecord.SpotImage>)
  }
}
