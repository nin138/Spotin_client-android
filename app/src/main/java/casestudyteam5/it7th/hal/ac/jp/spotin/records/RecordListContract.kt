package casestudyteam5.it7th.hal.ac.jp.spotin.records

interface RecordListContract {
  interface View {
    fun showList()
    fun showSortMonth()
    fun showSortDays()
    fun deleteRecord()
    fun showDetail(place_id: String)//詳細画面への遷移
    fun addTravelRecord(place_id: String)//記録追加画面へ遷移
    fun showNoRecordMessage()
  }

  interface Presenter {
    // TODO: fun loadList(list: List<SpotSrore>)
    // TODO: fun loadSortMonth(list: List<SpotStore>)
    // TODO: fun loadSortDays(list: List<SpotStore>)
    // TODO: fun openDetail(spot: SpotStore)
    fun sortList(recordSortType: RecordSortType)
    fun deleteTravelRecord(place_id: String)
    fun loadTravelList()
  }
}