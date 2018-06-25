package casestudyteam5.it7th.hal.ac.jp.spotin.view.records

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotStore
import java.text.SimpleDateFormat

class RecyclerAdapter(
  private val context: Context,
  private val itemClickListener: RecyclerViewHolder.ItemClickListener,
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
    holder.let {
      it.spotStore = itemList.get(position)
      val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd E")
      it.itemDays.text = simpleDateFormat.format(itemList.get(position).travelRecord.date)
      it.itemComment.text = itemList.get(position).travelRecord.comment
      it.itemPlaceName.text = itemList.get(position).travelRecord.place_name
      if (itemList.get(position).spotImageList?.size != 0) {
        val parcelFileDescriptor = context.contentResolver
          .openFileDescriptor(Uri.parse(itemList.get(position).spotImageList?.get(0)?.image_pass), "r")
        if (parcelFileDescriptor != null) {
          val fileDescriptor = parcelFileDescriptor.fileDescriptor
          val bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
          parcelFileDescriptor.close()
          holder.itemImage.setImageBitmap(bitmap)
        }
      }

      itemList.get(position).spotImageList?.forEach { Log.d("imagepass", it.image_pass) }
      it.itemView.setOnClickListener { itemClickListener.onItemClick(it, itemList.get(position)) }
    }
  }
}
