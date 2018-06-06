package casestudyteam5.it7th.hal.ac.jp.spotin.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import casestudyteam5.it7th.hal.ac.jp.spotin.data.RoomConverter
import casestudyteam5.it7th.hal.ac.jp.spotin.data.Spot

@Database(entities = [Spot::class], version = 1)
@TypeConverters(RoomConverter::class)
abstract class SpotDatabase: RoomDatabase(){
  abstract fun spotDao() : SpotDao


  companion object {

    private var INSTANCE: SpotDatabase? = null

    private val lock = Any()


    fun getInstance(context: Context): SpotDatabase {
      synchronized(lock) {
        if (INSTANCE == null) {
          //データベースの作成
          INSTANCE = Room.databaseBuilder(context.applicationContext, SpotDatabase::class.java, "Tasks.db").build()
        }

        return INSTANCE!!
      }
    }


  }


}