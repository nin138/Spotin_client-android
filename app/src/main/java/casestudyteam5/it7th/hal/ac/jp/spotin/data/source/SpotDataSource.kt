package casestudyteam5.it7th.hal.ac.jp.spotin.data.source

import casestudyteam5.it7th.hal.ac.jp.spotin.data.TravelRecord
import java.util.Date

interface SpotDataSource {
  interface LoadSpotCallback {
    fun onLoadSuccess(travelRecordList: List<TravelRecord>)

    fun onLoadFailed()
  }
  interface GetSpotCallback {
    fun onGetSpot(spot: TravelRecord)
    fun onGetFailedSpot()
  }

  fun getAllSpot(loadSpotCallback: LoadSpotCallback)

  fun getSpotDate(date: Date, loadSpotCallback: LoadSpotCallback)

  fun getSpotPlace(place_id: String, getSpotCallback: GetSpotCallback)

  fun saveSpot(travelRecord: TravelRecord)

  fun addSpotImage(spotimageList: List<TravelRecord.SpotImage>)

  fun deleteSpot(travelRecord: TravelRecord)

  fun deleteSpotImage(imagepass: String)
}