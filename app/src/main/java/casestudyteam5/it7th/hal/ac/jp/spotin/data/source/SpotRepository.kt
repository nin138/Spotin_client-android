package casestudyteam5.it7th.hal.ac.jp.spotin.data.source

import casestudyteam5.it7th.hal.ac.jp.spotin.data.Spot

class SpotRepository(private val spotLocalDataSource: SpotDataSource) : SpotDataSource {

  override fun getSpot(loadSpotCallback: SpotDataSource.GetSpotCallback) {

    spotLocalDataSource.getSpot(object : SpotDataSource.GetSpotCallback {
      override fun loadSuccess(spotList: List<Spot>) {
        loadSpotCallback.loadSuccess(spotList)
      }

      override fun loadFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }
    })
  }

  override fun saveSpot(spot: Spot) {
    spotLocalDataSource.saveSpot(spot)
  }

  override fun savaSpotList(spotList: List<Spot>) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun deleteSpot(spot: Spot) {
    spotLocalDataSource.deleteSpot(spot)
  }

  companion object {

    private var INSTANCE: SpotRepository? = null

    @JvmStatic fun getInstance(spotLocalDataSource: SpotDataSource): SpotRepository {
      return INSTANCE ?: SpotRepository(spotLocalDataSource)
        .apply { INSTANCE = this }
    }
  }
}