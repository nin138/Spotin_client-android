package casestudyteam5.it7th.hal.ac.jp.spotin.view.records

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotStore
import java.text.SimpleDateFormat

class RecyclerAdapter(
  private val context: Context,
  private val itemClickListener: RecyclerViewHolder.onItemClickListener,
  private val imageClickListener: ImageCarouselRecyclerViewAdapter.ViewHolder.OnImageClickListener,
  private val itemList: List<SpotStore>
) : RecyclerView.Adapter<RecyclerViewHolder>() {

  private var mRecyclerView: RecyclerView? = null

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    this.mRecyclerView = recyclerView
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    this.mRecyclerView = null
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
    val layoutInflater = LayoutInflater.from(context)
    val mView = layoutInflater.inflate(R.layout.list_item, parent, false)
    return RecyclerViewHolder(mView)
  }

  override fun getItemCount(): Int {
    return itemList.size
  }

  override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
    val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd E")
    holder.let {
      it.spotStore = itemList[position]
      it.itemDays.text = simpleDateFormat.format(itemList[position].travelRecord.date)
      it.itemComment.text = itemList[position].travelRecord.comment
      it.itemPlaceName.text = itemList[position].travelRecord.place_name
      it.itemView.setOnClickListener { itemClickListener.onItemClick(it, itemList[position]) }
    }
    //nest recyclerview
    itemList[position].let {
      val linearLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
      holder.nestRecycle.layoutManager = linearLayoutManager
      holder.nestRecycle.adapter = ImageCarouselRecyclerViewAdapter(context, it, imageClickListener)
    }
  }
}
