package casestudyteam5.it7th.hal.ac.jp.spotin.data.source

import casestudyteam5.it7th.hal.ac.jp.spotin.data.Spot
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.local.SpotLocalDataSource

class SpotRepository(val spotLocalDataSource: SpotDataSource) : SpotDataSource {


  override fun getSpot(loadSpotCallback: SpotDataSource.GetSpotCallback) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates../
    spotLocalDataSource.getSpot(object : SpotDataSource.GetSpotCallback{
      override fun loadSuccess(spotList: List<Spot>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }

      override fun loadFailed() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }

    })

  }

  override fun saveSpot(spot: Spot) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    spotLocalDataSource.saveSpot(spot)
  }

  override fun deleteSpot(spot: Spot) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    spotLocalDataSource.deleteSpot(spot)
  }



  companion object {

    private var INSTANCE: SpotRepository? = null


    @JvmStatic fun getInstance(spotLocalDataSource: SpotDataSource): SpotRepository {
      return INSTANCE ?: SpotRepository(spotLocalDataSource)
        .apply { INSTANCE = this }
    }


    @JvmStatic fun destroyInstance() {
      INSTANCE = null
    }
  }
}