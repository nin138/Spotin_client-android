package casestudyteam5.it7th.hal.ac.jp.spotin.records

import java.util.Date

interface RecordListContract {
  interface View {
    fun showList()
    fun showSortMonth()
    fun showSortDays()
    fun deleteRecord()
    fun showDetail(place_id: String)//intent
    fun addComment()
    fun addImage(isUpdata: Boolean)//intent
    fun showNoRecord()
  }
  interface Presenter {
    //fun loadList(list: List<SpotSrore>)
    //fun loadSortMonth(list: List<SpotStore>)
    //fun loadSortDays(list: List<SpotStore>)
    fun sortManth(date: Date)
    fun sortDays(date: Date)
    //fun openDetail(spot: SpotStore)
    fun upDataComment(comment: String)
    fun loadTravelList()
    fun result(): Boolean
  }
}