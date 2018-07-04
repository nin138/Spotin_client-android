package casestudyteam5.it7th.hal.ac.jp.spotin.data.source.local

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.arch.persistence.room.migration.Migration
import android.content.Context
import casestudyteam5.it7th.hal.ac.jp.spotin.data.DateTypeConverter
import casestudyteam5.it7th.hal.ac.jp.spotin.data.LocationRecord
import casestudyteam5.it7th.hal.ac.jp.spotin.data.TravelRecord

@Database(entities = [
  (TravelRecord::class),
  (TravelRecord.SpotImage::class),
  (LocationRecord::class)],
  version = 1, exportSchema = true)
@TypeConverters(DateTypeConverter::class)
abstract class SpotDatabase : RoomDatabase() {
  abstract fun spotDao(): SpotDao
  abstract fun locationDao(): LocationDao

  companion object {

    private var INSTANCE: SpotDatabase? = null

    private val lock = Any()

    fun getInstance(context: Context): SpotDatabase {
      synchronized(lock) {
        if (INSTANCE == null) {
          //データベースの作成
          INSTANCE = Room.databaseBuilder(context.applicationContext, SpotDatabase::class.java, "Spot.db")
            .addMigrations(object : Migration(1, 2) {
              override fun migrate(database: SupportSQLiteDatabase) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
              }
            }).build()
        }
        return INSTANCE!!
      }
    }
  }
}
