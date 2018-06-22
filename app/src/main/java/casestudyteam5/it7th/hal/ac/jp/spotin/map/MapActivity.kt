package casestudyteam5.it7th.hal.ac.jp.spotin.map

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
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

class MapActivity : AppCompatActivity(), MapContract.View, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

  var map: GoogleMap? = null
  private var yourMarker: Marker? = null
  private var range: Circle? = null
  private var gps: GPS? = null
  private val presenter: MapContract.Presenter = MapPresenter(this)

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
    presenter.onMarkerClicked(marker)
    return false
  }

  override fun updateYouAreHere(location: LatLng) {
    map?.animateCamera(CameraUpdateFactory.newLatLng(location))
    yourMarker?.remove()
    yourMarker = map?.addMarker(createYourHereMarkerOption(location))
    range?.remove()
    range = map?.addCircle(createCircleOption(location))
  }

  override fun removeSpotMarkers(markers: List<Marker>) {
    markers.forEach { it.remove() }
  }

  override fun setSpotMarkers(spots: List<SpotApi.Spot>) {
    if (map == null) return
    val list = spots.map {
      val marker = map!!.addMarker(createSpotMarkerOption(
        lat = it.lat.toDouble(),
        lng = it.lng.toDouble(),
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
