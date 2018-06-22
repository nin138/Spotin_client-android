package casestudyteam5.it7th.hal.ac.jp.spotin.records

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.data.DBFactory
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotStore
import kotlinx.android.synthetic.main.activity_travel_record_list.*

class TravelRecordListActivity : AppCompatActivity(), RecordListContract.View, RecyclerViewHolder.ItemClickListener {

    lateinit var presenter: RecordListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel_record_list)
        presenter = RecordListPresenter(this, DBFactory.provideSpotRepository(this))
        presenter.loadList()
    }

    override fun showList(list: List<SpotStore>) {
      mainRecyclerView.adapter = RecyclerAdapter(this, this, list)
      mainRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onItemClick(view: View, position: Int) {
        //TODO: 詳細へ遷移
    }

    override fun showSortMonth() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSortDays() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteRecord() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showDetail(place_id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTravelRecord(place_id: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showNoRecordMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
