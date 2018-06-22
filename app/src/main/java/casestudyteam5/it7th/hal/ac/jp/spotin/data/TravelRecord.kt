package casestudyteam5.it7th.hal.ac.jp.spotin.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity(tableName = "travel_record")
class TravelRecord(
  @PrimaryKey @ColumnInfo(name = "place_id") var place_id: String = "",
  @ColumnInfo(name = "comment") var comment: String = "",
  @ColumnInfo(name = "place_name") var place_name: String = "",
  @ColumnInfo(name = "date") var date: Date
) {

  val isEmpty
    get() = comment.isEmpty() && place_name.isEmpty()

  @Entity(tableName = "spot_image",
    foreignKeys = (arrayOf(ForeignKey(entity = TravelRecord::class,
    parentColumns = arrayOf("place_id"),
    childColumns = arrayOf("place_id"),
    onDelete = ForeignKey.CASCADE))))
  data class SpotImage(
    @ColumnInfo var place_id: String?,
    @ColumnInfo var image_pass: String? = null
  ) {
    @PrimaryKey(autoGenerate = true) var id: Int = 0
  }
}
