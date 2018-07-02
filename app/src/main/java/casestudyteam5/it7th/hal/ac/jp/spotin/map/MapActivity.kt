package casestudyteam5.it7th.hal.ac.jp.spotin.map

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.addrecord.AddRecordActivity
import casestudyteam5.it7th.hal.ac.jp.spotin.service.api.SpotApi
import casestudyteam5.it7th.hal.ac.jp.spotin.service.gps.GPS
import casestudyteam5.it7th.hal.ac.jp.spotin.util.PermissionUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Circle
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import android.view.LayoutInflater
import android.view.View

class MapActivity : DaggerAppCompatActivity(), MapContract.View, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

  var map: GoogleMap? = null
  private var yourMarker: Marker? = null
  private var range: Circle? = null
  private var gps: GPS? = null
  @Inject
  lateinit var presenter: MapPresenter
  var PLACE_PICKER_REQUEST = 1

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map)
    val mapFragment = supportFragmentManager
      .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
  }

  override fun onResume() {
    super.onResume()
    gps = GPS(this)
    if (PermissionUtil.isPermissionGranted(this, GPS.PERMISSION)) startGPS()
    else PermissionUtil.requestPermission(this, GPS.PERMISSION, GPS.REQUEST_CODE, "REQUIRE PERMISSION GPS ", "msg" )
  }
  override fun onPause() {
    super.onPause()
    gps?.stop()
  }

  override fun onMapReady(map: GoogleMap) {
    this.map = map
    map.setOnMarkerClickListener(this)
    map.isIndoorEnabled = false
    map.moveCamera(CameraUpdateFactory.zoomTo(17f))
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == GPS.REQUEST_CODE &&
      grantResults.isNotEmpty() &&
      grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      startGPS()
    } else finish()
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    val dialog = SpotDialog()
    // 各種ボタン押下時の処理
    dialog.onOkClickListener = DialogInterface.OnClickListener { dialog, id ->
      presenter.onMarkerClicked(marker)
    }
    dialog.onCancelClickListener = DialogInterface.OnClickListener { dialog, id ->
    }

    dialog.show(fragmentManager, "tag")

    return false
  }

  override fun updateYouAreHere(location: LatLng) {
    map?.animateCamera(CameraUpdateFactory.newLatLng(location))
    if (yourMarker != null) yourMarker?.position = location
    else yourMarker = map?.addMarker(createYourHereMarkerOption(location))
    if (range != null) range?.center = location
    else range = map?.addCircle(createCircleOption(location))
  }

  override fun removeSpotMarkers(markers: List<Marker>) {
    Log.d("maker::rm", markers.size.toString())
    markers.forEach { it.remove() }
  }

  override fun setSpotMarkers(spots: List<SpotApi.Spot>) {
    if (map == null) return
    val list = spots.map {
      val marker = map!!.addMarker(createSpotMarkerOption(
        lat = it.lat,
        lng = it.lng,
        name = it.name))
      MapPresenter.MarkerData(it, marker)
    }
    presenter.onMarkerAdded(list)
  }

  override fun startAddRecordActivity(spotId: String, spotName: String) {
    val intent = Intent(this, AddRecordActivity::class.java)
    intent.putExtra("place_id", spotId)
    intent.putExtra("place_name", spotName)
    startActivity(intent)
  }

  private fun startGPS() {
    Log.d("gps", "started")
    val location = gps?.getLastLocation()
    if (location != null) presenter.onLocationUpdated(LatLng(location.latitude, location.longitude))
    gps?.listen(presenter.locationListener)
  }

  private fun createSpotMarkerOption(lat: Double, lng: Double, name: String): MarkerOptions {
    return MarkerOptions()
      .position(LatLng(lat, lng))
      .title(name)
  }

  private fun createYourHereMarkerOption(position: LatLng): MarkerOptions {
    return MarkerOptions()
      .icon(BitmapDescriptorFactory.fromResource(R.drawable.a))
      .position(position)
  }

  private fun createCircleOption(position: LatLng): CircleOptions {
    return CircleOptions()
      .center(position)
      .radius(200.0)
      .strokeColor(Color.BLACK)
      .strokeWidth(5f)
      .fillColor(0x30ff0000)
  }
}

class SpotDialog : DialogFragment() {
  var onOkClickListener: DialogInterface.OnClickListener? = null
  var onCancelClickListener: DialogInterface.OnClickListener? = null
  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val builder = AlertDialog.Builder(activity)
    val inflater: LayoutInflater = LayoutInflater.from(activity)
    val content: View = inflater.inflate(R.layout.dialog_setting, null)

    builder.setView(content)

    builder.setMessage("Spot")
      .setPositiveButton("はい", onOkClickListener)
      .setNegativeButton("キャンセル", onCancelClickListener)
    return builder.create()
  }

  override fun onPause() {
    super.onPause()
    // onPause でダイアログを閉じる場合
    dismiss()
  }
}
