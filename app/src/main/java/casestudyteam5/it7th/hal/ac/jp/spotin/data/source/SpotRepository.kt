package casestudyteam5.it7th.hal.ac.jp.spotin.data.source

import casestudyteam5.it7th.hal.ac.jp.spotin.data.TravelRecord
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.local.SpotDao
import casestudyteam5.it7th.hal.ac.jp.spotin.util.AppExecutor
import java.util.Date

class SpotRepository(val spotDao: SpotDao, val appExecutor: AppExecutor) : SpotDataSource {
  override fun getAllSpot(loadSpotCallback: SpotDataSource.LoadSpotCallback) {
    appExecutor.localExecutor.execute {
      val spot = spotDao.getAllSpot()
      appExecutor.mainThread.execute {
        if (spot.isEmpty()) {
          loadSpotCallback.onLoadFailed()
        } else {
          loadSpotCallback.onLoadSuccess(spot)
        }
      }
    }
  }

  override fun getSpotDate(date: Date, loadSpotCallback: SpotDataSource.LoadSpotCallback) {
    appExecutor.localExecutor.execute {
      val spot = spotDao.getSpotData(date)
      appExecutor.mainThread.execute {
        if (spot.isEmpty()) {
          loadSpotCallback.onLoadFailed()
        } else {
          loadSpotCallback.onLoadSuccess(spot)
        }
      }
    }
  }

  override fun getSpotPlace(place_id: String, getSpotCallback: SpotDataSource.GetSpotCallback) {
    appExecutor.localExecutor.execute {
      val spot = spotDao.getSpotPlace(place_id)
      appExecutor.mainThread.execute {
        if (spot != null) {
          getSpotCallback.onGetSpot(spot)
        } else {
          getSpotCallback.onGetFailedSpot()
        }
      }
    }
  }

  override fun saveSpot(travelRecord: TravelRecord) {
    appExecutor.localExecutor.execute { spotDao.insertSpot(travelRecord) }
  }

  override fun upDataSpot(travelRecord: TravelRecord) {
    appExecutor.localExecutor.execute { spotDao.upDateSpot(travelRecord) }
  }

  override fun addSpotImage(spotimageList: List<TravelRecord.SpotImage>) {
    appExecutor.localExecutor.execute { spotDao.addSpotImage(spotimageList) }
  }

  override fun deleteSpot(travelRecord: TravelRecord) {
    appExecutor.localExecutor.execute { spotDao.deletSpot(travelRecord) }
  }

  override fun deleteSpotImage(imagepass: String) {
    appExecutor.localExecutor.execute { spotDao.deleteSpotImage(imagepass) }
  }

  companion object {
    private var INSTANCE: SpotRepository? = null

    //DAO,非同期処理用のインスタンス生成
    @JvmStatic
    fun getInstance(spotDao: SpotDao, appExecutor: AppExecutor): SpotRepository {
      if (INSTANCE == null) {
        synchronized(SpotRepository::javaClass) {
          INSTANCE = SpotRepository(spotDao, appExecutor)
        }
      }
      return INSTANCE!!
    }
  }
}
