package casestudyteam5.it7th.hal.ac.jp.spotin.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import java.util.Date

@Entity(primaryKeys = arrayOf("place_id", "image_pass"))
class Spot(
  @ColumnInfo(name = "place_id") var place_id: String = "",
  @ColumnInfo(name = "image_pass") var imagepass: String = "",
  @ColumnInfo(name = "comment") var comment: String = "",
  @ColumnInfo(name = "place_name") var place_name: String = "",
  @ColumnInfo(name = "date") var date: Date
) {

  val isEmpty
    get() = comment.isEmpty() && place_name.isEmpty()
}