package casestudyteam5.it7th.hal.ac.jp.spotin.data.source

import java.util.Date
//取得データ格納用
data class SpotStore(
  var place_id: String = "",
  var comment: String = "",
  var place_name: String = "",
  var date: Date? = null,
  var image_pass: String? = ""
)