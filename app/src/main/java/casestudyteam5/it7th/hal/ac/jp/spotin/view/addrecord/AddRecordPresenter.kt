package casestudyteam5.it7th.hal.ac.jp.spotin.view.addrecord

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import casestudyteam5.it7th.hal.ac.jp.spotin.data.TravelRecord
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotDataSource
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotRepository
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotStore
import java.util.Date
import java.io.File

class AddRecordPresenter (
  val context: Context,
  val view: AddRecordContract.View,
  val spotRepository: SpotRepository
) : AddRecordContract.Presenter {
  var imagepassList: MutableList<TravelRecord.SpotImage> = mutableListOf()

  override fun getCameraUri(context: Context): Uri {
    val imageName = "${System.currentTimeMillis()}.jpg"
    val contentValues = ContentValues()
    contentValues.put(MediaStore.Images.Media.TITLE, imageName)
    contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
  }

  fun isFileExist(imagepass: Uri?): Boolean {
    imagepass?.let {
      val path = getPath(it)
      val file = File(path)
      //val file = Uri.fromFile(File(path))
      if (file.exists()) return true
    }
    return false
  }

  private fun getPath(uri: Uri): String {
    var path = uri.toString()
    if (path.matches("^file:.*".toRegex())) {
      return path.replaceFirst("file://".toRegex(), "")
    } else if (!path.matches("^content:.*".toRegex())) {
      return path
    }
    val contentResolver = context.applicationContext.contentResolver
    val columns = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = contentResolver.query(uri, columns, null, null, null)
    if (cursor != null) {
      if (cursor.count > 0) {
        cursor.moveToFirst()
        path = cursor.getString(0)
      }
      cursor.close()
    }
    return path
  }

  override fun saveRecord(
    place_id: String,
    comment: String,
    place_name: String
  ) {
    //ユニーク値確認
    spotRepository.getSpotPlace(place_id, object : SpotDataSource.GetSpotCallback {
      override fun onGetSpot(spot: SpotStore) {
        upDataTravelRecord(place_id, comment, place_name, spot.travelRecord.date)
        imagepassList.let { upDataImageRecord(place_id, imagepassList) }
      }

      override fun onGetFailedSpot() {
        createTravelRecord(place_id, comment, place_name, date = Date())
        imagepassList.let { createImageRecord(place_id, imagepassList) }
      }
    })
  }

  override fun showImage() {
    view.showImageList(imagepassList)
  }

  override fun editImageList(imagepass: TravelRecord.SpotImage): List<TravelRecord.SpotImage> {
    imagepassList.add(imagepass)
    return imagepassList
  }

  override fun getImageList(): List<TravelRecord.SpotImage> {
    return imagepassList
  }

  override fun deleteListPosition(position: Int) {
    imagepassList.removeAt(position)
    view.showImageList(imagepassList)
  }

  override fun createTravelRecord(place_id: String, comment: String, place_name: String, date: Date) {
    //TODO:Entityにセット
    val travelRecord = TravelRecord(place_id, comment, place_name, date)
    spotRepository.saveSpot(travelRecord)
  }

  override fun createImageSpot(place_id: String, imagepass: Uri?): TravelRecord.SpotImage {
    return TravelRecord.SpotImage(place_id, imagepass.toString())
  }

  override fun createImageRecord(place_id: String, imagepassList: List<TravelRecord.SpotImage>) {
    spotRepository.addSpotImage(this.imagepassList)
  }

  override fun upDataTravelRecord(place_id: String, comment: String, place_name: String, date: Date) {
    val travelRecord = TravelRecord(place_id, comment, place_name, date)
    spotRepository.upDataSpot(travelRecord)
  }

  override fun upDataImageRecord(place_id: String, imagepassList: List<TravelRecord.SpotImage>) {
    spotRepository.addSpotImage(this.imagepassList)
  }
}
