package casestudyteam5.it7th.hal.ac.jp.spotin.data

import android.arch.persistence.room.TypeConverter
import android.net.Uri
import java.text.SimpleDateFormat
import java.util.*

class RoomTypeConverter {
  @TypeConverter
  fun fromDate(value: String?): Date? {
    return if (value == null) null else SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse(value)
  }

  @TypeConverter
  fun toTimestamp(date: Date?): String? {
    return SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(date)
  }

  @TypeConverter
  fun fromUri(value: String?): Uri? {
    return Uri.parse(value)
  }

  @TypeConverter
  fun toUri(uri: Uri?): String? {
    return uri.toString()
  }
}