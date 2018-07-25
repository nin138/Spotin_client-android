package casestudyteam5.it7th.hal.ac.jp.spotin.view.map

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.R.layout.category_select_model
import casestudyteam5.it7th.hal.ac.jp.spotin.data.Category

class CategorySelectAdapter(
  private var c: Context
) : BaseAdapter() {

  override fun getCount(): Int {
    return Category.values().size
  }

  override fun getItem(position: Int): Any {
    return Category.values()[position]
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    var convertView = convertView
    if (convertView == null) {
      val inflater = c.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
      convertView = inflater.inflate(category_select_model, null)
    }
    val imgView = convertView!!.findViewById<ImageView>(R.id.categoryIconImage)
    val txtView = convertView.findViewById<TextView>(R.id.categoryName)

    val cate = Category.values()[position]
    imgView.setImageResource(cate.image)
    txtView.text = cate.nameForText

    return convertView
  }
}
