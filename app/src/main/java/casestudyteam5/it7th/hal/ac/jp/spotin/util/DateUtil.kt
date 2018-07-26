package casestudyteam5.it7th.hal.ac.jp.spotin.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Date型の変換
 * 例
 * 変換前：Thu Jul 05 10:55:01 GMT+09:00 2018
 * 変換後：Thu Jul 05 00:00:00 GMT+09:00 2018
 */
class DateUtil {
  fun toDate(date: Date): Date? {
    val df = SimpleDateFormat("yyyy/MM/dd")
    return df.let {
      try {
        it.parse(it.format(date))
      } catch (e: ParseException) {
        null
      }
    }
  }
}
