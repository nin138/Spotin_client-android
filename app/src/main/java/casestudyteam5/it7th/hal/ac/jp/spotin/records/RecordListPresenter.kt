package casestudyteam5.it7th.hal.ac.jp.spotin.records

import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotDataSource
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotRepository
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotStore

class RecordListPresenter(
  val view: RecordListContract.View,
  val spotRepository: SpotRepository
) : RecordListContract.Presenter {

  override fun loadList() {
    spotRepository.getAllSpot(object : SpotDataSource.LoadSpotCallback {

      override fun onLoadSuccess(travelRecordList: List<SpotStore>) {
        view.showList(travelRecordList)
      }

      override fun onLoadFailed() {
        //TODO:　取得できなかった場合の処理
        view.showNoRecordMessage()
      }
    })
  }

  override fun openDetail(spot: SpotStore) {
//    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun sortList(recordSortType: RecordSortType) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun deleteTravelRecord(place_id: String) {
    //spotRepository.deleteSpot()
  }
}
