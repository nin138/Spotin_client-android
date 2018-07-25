package casestudyteam5.it7th.hal.ac.jp.spotin.view.map

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.service.api.entity.Spot
import casestudyteam5.it7th.hal.ac.jp.spotin.view.addrecord.AddRecordActivity
import casestudyteam5.it7th.hal.ac.jp.spotin.service.gps.GPS
import casestudyteam5.it7th.hal.ac.jp.spotin.util.PermissionUtil
import casestudyteam5.it7th.hal.ac.jp.spotin.view.records.TravelRecordListActivity
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
import kotlinx.android.synthetic.main.activity_map.*
import javax.inject.Inject

class MapActivity : DaggerAppCompatActivity(), MapContract.View, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

  var map: GoogleMap? = null
  private var yourMarker: Marker? = null
  private var range: Circle? = null
  private var gps: GPS? = null
  @Inject
  lateinit var presenter: MapPresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map)
    val mapFragment = supportFragmentManager
      .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)

    map_category.setOnClickListener {
      CategorySelectFragment().show(fragmentManager, "CategorySelect")
    }
    map_diary.setOnClickListener {
      val intent = Intent(this@MapActivity, TravelRecordListActivity::class.java)
      startActivity(intent)
    }
  }

  override fun onResume() {
    super.onResume()
    gps = GPS(this)
    PermissionUtil.RequestBuilder
      .withActivity(this)
      .permission(GPS.PERMISSION)
      .rationale(title = "permission requireed", message = "くれ") { finish() }
      .onPermissionDenied { finish() }
      .onPermissionGranted { startGPS() }
      .build()
      .check()
  }

  override fun onPause() {
    super.onPause()
    gps?.stop()
    presenter.onPause()
  }

  override fun onMapReady(map: GoogleMap) {
    this.map = map
    map.setOnMarkerClickListener(this)
    map.isIndoorEnabled = false
    map.moveCamera(CameraUpdateFactory.zoomTo(17f))
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    val inflater: LayoutInflater = LayoutInflater.from(this)
    val content = inflater.inflate(R.layout.dialog_setting, null)
    val builder = AlertDialog.Builder(this, R.style.dialog_setting).setView(content).create()
    builder.setCanceledOnTouchOutside(false)
    builder.window.decorView
      .startAnimation(AnimationUtils.loadAnimation(this, R.anim.in_animation))

    content.findViewById<TextView>(R.id.name)?.text = marker?.title
    val add: Button = content.findViewById(R.id.add)
    val cancel: Button = content.findViewById(R.id.cancel)

    add.setOnClickListener { marker?.let { presenter.onMarkerClicked(it) } }
    cancel.setOnClickListener { builder.dismiss() }
    builder.show()
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

  override fun setSpotMarkers(spots: List<Spot>) {
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
