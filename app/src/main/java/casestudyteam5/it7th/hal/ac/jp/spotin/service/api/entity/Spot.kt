package casestudyteam5.it7th.hal.ac.jp.spotin.service.api.entity

data class Spot(
  val lat: Double,
  val lng: Double,
  val place_id: String,
  val name: String,
  val icon: String
)

data class Spots(
  val spot: List<Spot>
)
