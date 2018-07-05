package casestudyteam5.it7th.hal.ac.jp.spotin.view.records

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

class HorizontalRecyclerViewAdapter(
  private val context: Context,
  travelRecord: List<TravelRecord.SpotImage>,
  private val imageClickListener: ViewHolder.OnImageClickListener
) :
  RecyclerView.Adapter<HorizontalRecyclerViewAdapter.ViewHolder>() {

  private var mList: List<TravelRecord.SpotImage>? = travelRecord

  override fun getItemCount(): Int {
    return mList!!.size
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(context)
    val mView = layoutInflater.inflate(R.layout.add_image_list, parent, false)
    //TODO:クリック処理
    return ViewHolder(mView)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.let {
      if (mList?.size != 0) {
        val parcelFileDescriptor = context.contentResolver
          .openFileDescriptor(Uri.parse(mList?.get(position)?.image_pass), "r") ?: return
        val fileDescriptor = parcelFileDescriptor.fileDescriptor
        val bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
        parcelFileDescriptor.close()
        it.itemImage.setImageBitmap(bitmap)
        it.itemImage.setOnClickListener {
          imageClickListener.onImageClick(it, Uri.parse(mList?.get(position)?.image_pass))
        }
      }
    }
  }

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    interface OnImageClickListener {
      fun onImageClick(view: View, uri: Uri)
    }

    val itemImage: ImageView = view.findViewById(R.id.addImage)
  }
}
