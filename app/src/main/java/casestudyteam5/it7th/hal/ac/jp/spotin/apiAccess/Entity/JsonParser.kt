package casestudyteam5.it7th.hal.ac.jp.spotin.apiAccess.Entity

data class SpotData(
  var lat: Double,
  var lng: Double,
  var spotID: String,
  var spotName: String,
  var spotCategory: String,
  var spotImgs: Array<String>
)

data class SpotDatas(val spotDatas: List<SpotData>) {
  companion object {
      fun of(vararg spotDatas: SpotData) = SpotDatas(listOf(*spotDatas))
  }
}
