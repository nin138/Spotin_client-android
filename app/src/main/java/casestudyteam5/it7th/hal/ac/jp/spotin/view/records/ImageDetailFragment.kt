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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ImageDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ImageDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ImageDetailFragment : Fragment() {
  // TODO: Rename and change types of parameters
  private lateinit var spotStore: SpotStore
  private var position: Int? = null
  private var mListener: ChildFragmentListener? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    arguments?.let {
      spotStore = it.getSerializable(ARG_PARAM1) as SpotStore
      position = it.getInt(ARG_PARAM2)
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
    detailRecycler.adapter = ImageDetailRecycler(this, activity!!.applicationContext, spotStore)
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

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   *
   *
   * See the Android Training lesson [Communicating with Other Fragments]
   * (http://developer.android.com/training/basics/fragments/communicating.html)
   * for more information.
   */
  interface OnFragmentInteractionListener {
    // TODO: Update argument type and name
    fun onFragmentInteraction(uri: Uri)
  }

  interface ChildFragmentListener {
    fun onCloseChild()
  }

  companion object {
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ImageDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    @JvmStatic
    fun newInstance(spotStore: SpotStore, position: Int) =
      ImageDetailFragment().apply {
        arguments = Bundle().apply {
          putSerializable(ARG_PARAM1, spotStore)
          putInt(ARG_PARAM2, position)
        }
      }
  }
}
