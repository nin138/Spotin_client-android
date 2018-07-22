package casestudyteam5.it7th.hal.ac.jp.spotin.view.records

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotStore
import casestudyteam5.it7th.hal.ac.jp.spotin.util.FlickListener

class ImageDetailRecyclerView(
  val imageDetailFragment: ImageDetailFragment,
  private val context: Context,
  spotStore: SpotStore
) :
  RecyclerView.Adapter<ImageDetailRecyclerView.ViewHolder>() {

  private var mList = spotStore.spotImageList

  override fun getItemCount(): Int {
    //画像枚数
    return mList!!.size
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(context)
    val mView = layoutInflater.inflate(R.layout.detail_image, parent, false)
    //TODO:クリック処理
    return ViewHolder(mView)
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    if (mList?.size != 0 && position <= itemCount) {
      val parcelFileDescriptor = context.contentResolver
        .openFileDescriptor(Uri.parse(mList?.get(position)?.image_pass), "r") ?: return
      val fileDescriptor = parcelFileDescriptor.fileDescriptor
      val bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor)
      parcelFileDescriptor.close()
      holder.itemImage.setImageBitmap(bitmap)
    }
    holder.itemImage.setOnClickListener { imageDetailFragment.closeFragment() }

    holder.itemImage.setOnTouchListener(FlickListener(object : FlickListener.Listener {
      override fun onFlickToLeft() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }

      override fun onFlickToRight() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }

      override fun onFlickToUp() {
        imageDetailFragment.closeFragment()
      }

      override fun onFlickToDown() {
        imageDetailFragment.closeFragment()
      }
    }))
  }

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val itemImage: ImageView = view.findViewById(R.id.ImagesDetail)
  }
}
