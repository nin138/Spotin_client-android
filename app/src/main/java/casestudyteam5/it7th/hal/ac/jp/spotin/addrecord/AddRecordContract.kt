package casestudyteam5.it7th.hal.ac.jp.spotin.addrecord

import android.content.Context
import android.net.Uri
import java.util.Date

interface AddRecordContract {

  interface View {
    val isUpdate: Boolean
    var imagepassList: List<Uri>
    fun takeImage() //画像の追加
    fun showImage(imagepass: Uri) //TODO: リスト化は一旦保留
    fun editComment()
    fun selfcheckPermmision()
    fun showEmpryError()
    fun cancel()
    fun decision()
  }
  interface Presenter {
    fun saveRecord(place_id: String, comment: String?, place_name: String, imagepassList: List<Uri>?)
    fun getcameraUri(context: Context): Uri
    fun editImageList(imagepass: Uri): List<Uri>
    fun createTravelRecord(place_id: String, comment: String?, place_name: String, date: Date)
    fun createImageRecord(imagepassList: List<Uri>)
    fun updateTravelRecord(place_id: String, comment: String?, place_name: String)
    fun updateImageRecord(imagepassList: List<Uri>)
  }
}