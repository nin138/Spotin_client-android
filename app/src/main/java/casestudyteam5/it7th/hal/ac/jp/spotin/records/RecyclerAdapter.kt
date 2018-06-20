package casestudyteam5.it7th.hal.ac.jp.spotin.records

import android.content.Context
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotStore

class RecyclerAdapter(private val context: Context,
                      private val itemClickListener: RecyclerViewHolder.ItemClickListener,
                      private val itemList:List<SpotStore>): RecyclerView.Adapter<RecyclerViewHolder>() {

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

    mView.setOnClickListener { view ->
      mRecyclerView?.let {
        itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
      }
    }

    return RecyclerViewHolder(mView)
  }

  override fun getItemCount(): Int {
    return itemList.size
  }

  override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
    holder?.let{
      it.itemDays.text = itemList.get(position).date.toString()
      it.itemComment.text = itemList.get(position).comment
      it.itemPlaceName.text = itemList.get(position).place_name
      it.itemImage.setImageURI(Uri.parse(itemList.get(position).image_pass))
    }
  }

}