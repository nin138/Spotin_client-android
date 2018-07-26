package casestudyteam5.it7th.hal.ac.jp.spotin.view.records

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotStore
import kotlinx.android.synthetic.main.fragment_image_detail.*

private const val ELEMENT = "spotparam"
private const val POSITION = "0"

class ImageDetailFragment : Fragment() {
  private lateinit var spotStore: SpotStore
  private var position: Int? = null
  private var mListener: ChildFragmentListener? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      spotStore = it.getSerializable(ELEMENT) as SpotStore
      position = it.getInt(POSITION)
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.fragment_image_detail, container, false)
    return inflater.inflate(R.layout.fragment_image_detail, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    detailRecycler.adapter = ImageDetailRecyclerView(this, activity!!.applicationContext, spotStore)
    detailRecycler.layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.HORIZONTAL, false)
    position?.let { detailRecycler.layoutManager.scrollToPosition(it) }
  }

  // TODO: Rename method, update argument and hook method into UI event
  fun onButtonPressed(uri: Uri) {
    mListener?.let { it.onCloseChild() }
  }

  override fun onAttach(context: Context) {
    super.onAttach(context)
    if (context is ChildFragmentListener) {
      mListener = context
    } else {
      throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
    }
  }

  override fun onDetach() {
    super.onDetach()
    mListener = null
  }

  fun closeFragment() {
    mListener?.onCloseChild()
  }

  interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    fun onFragmentInteraction(uri: Uri)
  }

  interface ChildFragmentListener {
    fun onCloseChild()
  }

  companion object {
    @JvmStatic
    fun newInstance(spotStore: SpotStore, position: Int) =
      ImageDetailFragment().apply {
        arguments = Bundle().apply {
          putSerializable(ELEMENT, spotStore)
          putInt(POSITION, position)
        }
      }
  }
}
