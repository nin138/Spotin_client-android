package casestudyteam5.it7th.hal.ac.jp.spotin.view.map

import android.app.DialogFragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.data.Category

class CategorySelectFragment : DialogFragment() {

  private lateinit var gv: GridView

  override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

    val rootView: View = inflater!!.inflate(R.layout.flagment_category_select, null)
    val mapAct = activity as MapActivity

    gv = rootView.findViewById(R.id.categorySelectGridView)
    dialog.setTitle("Category select")
    gv.adapter = CategorySelectAdapter(activity.applicationContext)

    gv.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
      mapAct.presenter.selectedCategory = Category.values()[position].cateName
      dismiss()
    }
    return rootView
  }
}
