package casestudyteam5.it7th.hal.ac.jp.spotin.records

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotStore

class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

  interface ItemClickListener {
    fun onItemClick(view: View, position: Int)
  }
  var spotStore: SpotStore? = null
  val itemDays: TextView = view.findViewById(R.id.recordItemDate)
  val itemImage: ImageView = view.findViewById(R.id.recordItemImage)
  val itemComment: TextView = view.findViewById(R.id.recordItemComment)
  val itemPlaceName: TextView = view.findViewById(R.id.recordItemPlace)

  init {
    // layoutの初期設定するなら
  }
}