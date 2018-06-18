package casestudyteam5.it7th.hal.ac.jp.spotin.addrecord

import android.net.Uri

interface AddRecordContract {
  interface View {
    val isUpdate: Boolean
    fun addImageList(imagepass: Uri)
    fun showImage(imagepass: Uri)
    fun editComment()
    fun showEmpryError()
    fun showPlace(place: String)
    fun cancel()
    fun decision()
  }
  interface Presenter {
    fun saveRecord()
    fun createTravelRecord(place_id: String, comment: String?, place_name: String)
    fun createImageRecord(imagepassList: List<Uri>)
    fun updateTravelRecord(place_id: String, comment: String?, place_name: String, imagepass: Uri)
    fun updateImageRecord(imagepassList: List<Uri>)
  }
}