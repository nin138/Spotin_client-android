package casestudyteam5.it7th.hal.ac.jp.spotin.view.records

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotStore

class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

  interface onItemClickListener {
    fun onItemClick(view: View, spotStore: SpotStore)
  }
  var spotStore: SpotStore? = null
  val itemDays: TextView = view.findViewById(R.id.recordItemDate)
  val itemComment: TextView = view.findViewById(R.id.recordItemComment)
  val itemPlaceName: TextView = view.findViewById(R.id.recordItemPlace)
  val nestRecycle: RecyclerView = view.findViewById(R.id.nest_recycler)

  init {
    // layoutの初期設定するなら
  }
}
