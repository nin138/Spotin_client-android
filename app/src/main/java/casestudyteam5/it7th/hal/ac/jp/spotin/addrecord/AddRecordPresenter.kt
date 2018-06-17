package casestudyteam5.it7th.hal.ac.jp.spotin.addrecord

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import java.io.File
import java.util.Date

class AddRecordPresenter(val view: AddRecordContract.View) : AddRecordContract.Presenter {
  lateinit var imagepassList: MutableList<Uri>
  //lateinit var travelRecord: TravelRecord

  override fun getcameraUri(context: Context): Uri {
    val imageName = "${System.currentTimeMillis()}.jpg"
    val contentValues = ContentValues()
    contentValues.put(MediaStore.Images.Media.TITLE, imageName)
    contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
  }

  fun getFileSchemeUri(contentCheme: Uri): Uri {
    var fileScheme: Uri = contentCheme
    fileScheme = Uri.fromFile(File(fileScheme.path))
    return fileScheme
  }

  override fun saveRecord(place_id: String, comment: String?, place_name: String, imagepassList: List<Uri>?) {
    if (view.isUpdate) {
      updateTravelRecord(place_id, comment, place_name)
      imagepassList?.let { updateImageRecord(it) }
    } else {
      createTravelRecord(place_id, comment, place_name, date = Date())
      createImageRecord(imagepassList = view.imagepassList)
    }
    //TODO:"データベースに挿入処理"
  }

  override fun editImageList(imagepass: Uri): List<Uri> {
    imagepassList = mutableListOf(imagepass)
    view.showImage(imagepass)
    return imagepassList
  }

  override fun createTravelRecord(place_id: String, comment: String?, place_name: String, date: Date) {
    //TODO:Entityにセット
  }

  override fun createImageRecord(imagepassList: List<Uri>) {
    //TODO:Entityにセット
  }

  override fun updateTravelRecord(place_id: String, comment: String?, place_name: String) {
    //TODO:Entityにセット
  }

  override fun updateImageRecord(imagepassList: List<Uri>) {
    //TODO:Entityにセット
  }
}