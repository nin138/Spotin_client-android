package casestudyteam5.it7th.hal.ac.jp.spotin.data.source.local

import android.arch.persistence.room.Room
import android.content.Context
import casestudyteam5.it7th.hal.ac.jp.spotin.data.Spot
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotDao
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotDataSource

class SpotLocalDataSource private constructor(val spotDao: SpotDao): SpotDataSource{
  //val context: Context? = null
  //contextの位置
  //val spotDatabase :SpotDatabase = Room.databaseBuilder(context!!.applicationContext, SpotDatabase::class.java, "Tasks.db").build()

  override fun getSpot(loadSpotCallback: SpotDataSource.GetSpotCallback) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    //データ取得処理
    val spot = spotDao.record()
    if(spot.isEmpty()){
      loadSpotCallback.loadFailed()
    } else {
      loadSpotCallback.loadSuccess(spot)
    }

  }

  override fun saveSpot(spot: Spot) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    //データベース保存処理
    spotDao.insertRecord(spot)
    //spotDatabase.spotDao().insertRecord(spot)
  }
  override fun deleteSpot(spot: Spot) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    //レコード削除処理
    spotDao.deletRecord(spot)
    //spotDatabase.spotDao().deletRecord(spot)
  }

  //Injectionでインスタンス化
  companion object {
    private var INSTANCE: SpotLocalDataSource? = null

    @JvmStatic
    fun getInstance(spotDao: SpotDao): SpotLocalDataSource {
      if (INSTANCE == null) {
        synchronized(SpotLocalDataSource::javaClass) {
          INSTANCE = SpotLocalDataSource(spotDao)
        }
      }
      return INSTANCE!!
    }

  }
}