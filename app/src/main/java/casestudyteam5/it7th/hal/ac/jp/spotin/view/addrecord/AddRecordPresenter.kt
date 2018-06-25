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

class AddRecordPresenter(
  val view: AddRecordContract.View,
  val spotRepository: SpotRepository
) : AddRecordContract.Presenter {

  var imagepassList: MutableList<TravelRecord.SpotImage> = mutableListOf()

  override fun getcameraUri(context: Context): Uri {
    val imageName = "${System.currentTimeMillis()}.jpg"
    val contentValues = ContentValues()
    contentValues.put(MediaStore.Images.Media.TITLE, imageName)
    contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
    return context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
  }

  override fun saveRecord(
    place_id: String,
    comment: String,
    place_name: String,
    imagepassList: List<TravelRecord.SpotImage>?
  ) {
    //ユニーク値確認
    spotRepository.getSpotPlace(place_id, object : SpotDataSource.GetSpotCallback {
      override fun onGetSpot(spot: SpotStore) {
        updataTravelRecord(place_id, comment, place_name, spot.travelRecord.date)
        imagepassList?.let { updataImageRecord(place_id, imagepassList) }
      }

      override fun onGetFailedSpot() {
        createTravelRecord(place_id, comment, place_name, date = Date())
        imagepassList?.let { createImageRecord(place_id, imagepassList) }
      }
    })
    //TODO:"データベースに挿入処理"
  }

  override fun editImageList(imagepass: TravelRecord.SpotImage): List<TravelRecord.SpotImage> {
    imagepassList.add(imagepass)
    view.showImageList(imagepassList)
    return imagepassList
  }

  fun deleteListPositon(position: Int) {
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

  override fun updataTravelRecord(place_id: String, comment: String, place_name: String, date: Date) {
    val travelRecord = TravelRecord(place_id, comment, place_name, date)
    spotRepository.upDataSpot(travelRecord)
  }

  override fun updataImageRecord(place_id: String, imagepassList: List<TravelRecord.SpotImage>) {
    spotRepository.addSpotImage(this.imagepassList)
  }
}
