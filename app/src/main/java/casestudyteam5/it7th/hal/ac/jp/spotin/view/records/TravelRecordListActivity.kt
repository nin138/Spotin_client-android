package casestudyteam5.it7th.hal.ac.jp.spotin.view.records

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.RelativeLayout
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.data.DBFactory
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotStore
import kotlinx.android.synthetic.main.activity_travel_record_list.*

class TravelRecordListActivity : AppCompatActivity(),
  RecordListContract.View,
  RecyclerViewHolder.onItemClickListener,
  HorizontalRecyclerViewAdapter.ViewHolder.OnImageClickListener,
  ImageDetailFragment.OnFragmentInteractionListener,
  ImageDetailFragment.ChildFragmentListener {

  override fun onFragmentInteraction(uri: Uri) {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  lateinit var presenter: RecordListPresenter
  private var behavior: BottomSheetBehavior<*>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_travel_record_list)
    presenter = RecordListPresenter(this, DBFactory.provideSpotRepository(this))
    presenter.loadList()
    val bottomSheet: RelativeLayout = findViewById(R.id.bottom_sheet)
    behavior = BottomSheetBehavior.from(bottomSheet)
  }

  override fun showList(list: List<SpotStore>) {
    mainRecyclerView.adapter = RecyclerAdapter(this, this, this, list)
    mainRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
  }

  override fun onItemClick(view: View, spotStore: SpotStore) {
    presenter.openDetail(spotStore)
    /*val ft = supportFragmentManager.beginTransaction()
    ft.replace(R.id.child_fragment, ImageDetailFragment.newInstance(spotStore, "code"))
    ft.commit()
    behavior!!.state = BottomSheetBehavior.STATE_EXPANDED*/
    //TODO: 詳細へ遷移
  }

  override fun onImageClick(view: View, position: Int, store: SpotStore) {
    onShowChild(store, position)
  }

  fun onShowChild(store: SpotStore, position: Int) {
    val ft = supportFragmentManager.beginTransaction()
    ft.replace(R.id.child_fragment, ImageDetailFragment.newInstance(store, position))
    ft.commit()
    behavior!!.state = BottomSheetBehavior.STATE_EXPANDED
  }

  override fun onCloseChild() {
    behavior!!.state = BottomSheetBehavior.STATE_HIDDEN
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
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}
