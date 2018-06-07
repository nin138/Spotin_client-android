package casestudyteam5.it7th.hal.ac.jp.spotin.data.source.local

import casestudyteam5.it7th.hal.ac.jp.spotin.data.Spot
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotDataSource
import casestudyteam5.it7th.hal.ac.jp.spotin.util.AppExecutor

class SpotLocalDataSource private constructor(val spotDao: SpotDao, val appExecutor: AppExecutor) : SpotDataSource {
  //val context: Context? = null
  //contextの位置
  //val spotDatabase :SpotDatabase = Room.databaseBuilder(context!!.applicationContext, SpotDatabase::class.java, "Tasks.db").build()

  override fun getSpot(loadSpotCallback: SpotDataSource.GetSpotCallback) {
    //データ取得処理

    appExecutor.localExecutor.execute {
      val spot = spotDao.getAllSpot()
      appExecutor.mainThread.execute {
        if (spot.isEmpty()) {
          loadSpotCallback.loadFailed()
        } else {
          loadSpotCallback.loadSuccess(spot)
        }
      }
    }
  }

  override fun saveSpot(spot: Spot) {
    //データベース保存処理
    appExecutor.localExecutor.execute { spotDao.insertSpot(spot) }
    //spotDatabase.spotDao().insertRecord(spot)
  }

  override fun savaSpotList(spotList: List<Spot>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
  override fun deleteSpot(spot: Spot) {
    //レコード削除処理
    appExecutor.localExecutor.execute { spotDao.deletSpot(spot) }
    //spotDatabase.spotDao().deletRecord(spot)
  }

  //Injectionでインスタンス化
  companion object {
    private var INSTANCE: SpotLocalDataSource? = null

    //DAO,非同期処理用のインスタンス生成
    @JvmStatic
    fun getInstance(spotDao: SpotDao, appExecutor: AppExecutor): SpotLocalDataSource {
      if (INSTANCE == null) {
        synchronized(SpotLocalDataSource::javaClass) {
          INSTANCE = SpotLocalDataSource(spotDao, appExecutor)
        }
      }
      return INSTANCE!!
    }
  }
}