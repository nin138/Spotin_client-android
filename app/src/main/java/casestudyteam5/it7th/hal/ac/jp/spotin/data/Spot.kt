package casestudyteam5.it7th.hal.ac.jp.spotin.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.net.Uri
import java.util.Date

@Entity
class Spot(
  @ColumnInfo var imagepass: Uri?,
  @ColumnInfo var comment: String = "",
  @ColumnInfo var place: String = "",
  @ColumnInfo var date: Date
) {
  @PrimaryKey(autoGenerate = true) var id: Int = 0
  var prace_id: String = ""

  val isEmpty
    get() = comment.isEmpty() && place.isEmpty()
}