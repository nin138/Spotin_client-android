package casestudyteam5.it7th.hal.ac.jp.spotin.view.addrecord

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.data.TravelRecord

class AddRecordRecyclerAdapter(
  private val context: Context,
  private val itemClickListener: ViewHolder.OnItemClickListener,
  private val spotImageList: List<TravelRecord.SpotImage>
) : RecyclerView.Adapter<AddRecordRecyclerAdapter.ViewHolder>() {

  private var mRecyclerView: RecyclerView? = null

  override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
    super.onAttachedToRecyclerView(recyclerView)
    this.mRecyclerView = recyclerView
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    super.onDetachedFromRecyclerView(recyclerView)
    this.mRecyclerView = null
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(context)
    val mView = layoutInflater.inflate(R.layout.add_image_list, parent, false)

    //要素の削除
    mView.setOnClickListener { view ->
      mRecyclerView?.let {
        itemClickListener.onItemClick(view, it.getChildAdapterPosition(view))
      }
    }
    return ViewHolder(mView)
  }

  override fun getItemCount(): Int {
    return this.spotImageList.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.let {
      it.spotImage = spotImageList[position]
      val parcelFileDescriptor = context.contentResolver
        .openFileDescriptor(Uri.parse(spotImageList[position].image_pass), "r") ?: return
      val fileDescriptor = parcelFileDescriptor.fileDescriptor
      val bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
      parcelFileDescriptor.close()
      it.itemImage.setImageBitmap(bitmap)
    }
  }

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    interface OnItemClickListener {
      fun onItemClick(view: View, position: Int)
    }

    var spotImage: TravelRecord.SpotImage? = null
    val itemImage: ImageView = view.findViewById(R.id.addImage)
  }
}
