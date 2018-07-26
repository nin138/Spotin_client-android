package casestudyteam5.it7th.hal.ac.jp.spotin.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import casestudyteam5.it7th.hal.ac.jp.spotin.data.LocationRecord
import java.util.Date

@Dao interface LocationDao {

  @Insert
  fun insertLocation(locationRecord: LocationRecord)

  @Query("SELECT * FROM location_record")
  fun getAllLocation(): List<LocationRecord>

  @Query("SELECT * FROM location_record WHERE location_recording = (:date)")
  fun getDateLocation(date: Date): List<LocationRecord>

  @Delete
  fun deleteLocation(locationRecord: LocationRecord)
}
