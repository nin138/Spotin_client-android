package casestudyteam5.it7th.hal.ac.jp.spotin.data.source

import casestudyteam5.it7th.hal.ac.jp.spotin.data.Spot

interface SpotDataSource {
  interface GetSpotCallback {

    fun loadSuccess(spotList: List<Spot>)

    fun loadFailed()
  }

  fun getSpot(loadSpotCallback: GetSpotCallback)

  fun saveSpot(spot: Spot)

  fun deleteSpot(spot: Spot)

  //fun deleteSpotImage(imagepass: String)
}