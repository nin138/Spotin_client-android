package casestudyteam5.it7th.hal.ac.jp.spotin.view.records

import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotStore
import java.util.Date

interface RecordListContract {

  interface View {
    fun showList(list: List<SpotStore>)
    fun showSortMonth()
    fun showSortDays(date: Date)
    fun deleteRecord()
    fun showDetail(place_id: String)//詳細画面への遷移
    fun addTravelRecord(place_id: String)//記録追加画面へ遷移
    fun showNoRecordMessage()
  }

  interface Presenter {
    fun loadList()
    fun openDetail(spot: SpotStore)
    fun sortList(recordSortType: RecordSortType)
    fun deleteTravelRecord(place_id: String)
    fun sortDateList(date: Date)
  }
}
