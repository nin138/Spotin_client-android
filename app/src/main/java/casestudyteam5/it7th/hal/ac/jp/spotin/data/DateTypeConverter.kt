package casestudyteam5.it7th.hal.ac.jp.spotin.data

import android.arch.persistence.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date

class DateTypeConverter {
  @TypeConverter
  fun fromDate(value: String?): Date? {
    return if (value == null) null else SimpleDateFormat("yyyy/MM/dd hh:mm:ss").parse(value)
  }

  @TypeConverter
  fun toDate(date: Date?): String? {
    return SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(date)
  }
}