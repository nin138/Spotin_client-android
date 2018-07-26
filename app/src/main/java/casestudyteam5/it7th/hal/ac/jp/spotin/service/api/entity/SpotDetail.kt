package casestudyteam5.it7th.hal.ac.jp.spotin.service.api.entity

data class SpotDetail(
  val lat: Double,
  val lng: Double,
  val place_id: String,
  val address: String,
  val phone: String,
  val open_now: Boolean,
  val opening_hours: List<String>,
  val types: List<String>,
  val rating: Double,
  val photos: List<String>,
  val name: String
)
