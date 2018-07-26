package casestudyteam5.it7th.hal.ac.jp.spotin.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity(tableName = "location_record")
data class LocationRecord(
  @ColumnInfo(name = "location_recording")var Recording: Date,
  @ColumnInfo(name = "place_id") var place_id: String?,
  @ColumnInfo(name = "location_lat") var locationLat: Double,
  @ColumnInfo(name = "location_lng") var locationLng: Double
) {
  @PrimaryKey(autoGenerate = true) var id: Int = 0
}
