package casestudyteam5.it7th.hal.ac.jp.spotin.data.source

import casestudyteam5.it7th.hal.ac.jp.spotin.data.LocationRecord
import casestudyteam5.it7th.hal.ac.jp.spotin.data.TravelRecord
import java.util.Date

interface SpotDataSource {
  interface LoadSpotCallback {
    fun onLoadSuccess(travelRecordList: List<SpotStore>)

    fun onLoadFailed()
  }
  interface GetSpotCallback {
    fun onGetSpot(spot: SpotStore)
    fun onGetFailedSpot()
  }

  interface LoadLocationCallback {
    fun onLoadSuccess(locationRecordList: List<LocationRecord>)

    fun onLoadFailed()
  }

  fun getAllSpot(loadSpotCallback: LoadSpotCallback)

  fun getSpotDate(date: Date, loadSpotCallback: LoadSpotCallback)

  fun getSpotPlace(place_id: String, getSpotCallback: GetSpotCallback)

  fun saveSpot(travelRecord: TravelRecord)

  fun addSpotImage(spotimageList: List<TravelRecord.SpotImage>)

  fun upDataSpot(travelRecord: TravelRecord)

  fun deleteSpot(travelRecord: TravelRecord)

  fun deleteSpotImage(imagepass: String)

  fun saveLocation(locationRecord: LocationRecord)

  fun getAllLocation(loadLocationCallback: LoadLocationCallback)

  fun getDateLocation(date: Date, loadLocationCallback: LoadLocationCallback)

  fun deleteLocation(locationRecord: LocationRecord)
}
