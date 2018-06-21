package casestudyteam5.it7th.hal.ac.jp.spotin.map

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

  data class MarkerData(val spot: SpotApi.Spot, val marker: Marker)

  var map: GoogleMap? = null
  private var markerList: List<MarkerData> = listOf()
  private var yourMarker: Marker? = null
  private var gps: GPS? = null
  private var range: Circle? = null

  private fun startGPS() {
    println("\n\n\n\ngps listen")
    val location = gps?.getLastLocation()
    if (location != null) onLocationUpdated(location)
    gps?.listen(object : LocationListener {
      override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }

      override fun onProviderEnabled(p0: String?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }

      override fun onProviderDisabled(p0: String?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
      }

      override fun onLocationChanged(location: Location?) {
        println("\n\n\n\n\nlocation: " + location?.latitude.toString() + "," + location?.longitude)
        if (location != null) onLocationUpdated(location)
      }
    })
  }

  override fun onMarkerClick(marker: Marker?): Boolean {
    markerList.forEach {
      if (it.marker == marker) {
        val intent = Intent(this, AddRecordActivity::class.java)
        intent.putExtra("place_id", it.spot.place_id)
        intent.putExtra("place_name", it.spot.name)
        startActivity(intent)
      }
    }
    return false
  }

  private fun onLocationUpdated(location: Location) {
    val position = LatLng(location.latitude, location.longitude)
    //TODO マーカーを差分のみアップデート
    map?.animateCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))
    //TODO 現在地
    yourMarker?.remove()
    yourMarker = map?.addMarker(MarkerOptions()
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.a))
        .position(position))
    range?.remove()
    range = map?.addCircle(CircleOptions()
      .center(position)
      .radius(200.0)
      .strokeColor(Color.BLACK)
      .strokeWidth(5f)
      .fillColor(0x30ff0000))
    setSpotsToMap("restaurant", location.latitude, location.longitude)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map)
    val mapFragment = supportFragmentManager
      .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
    println(PermissionUtil.isPermissionGranted(this, GPS.PERMISSION).toString())
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

  private fun setSpotsToMap(category: String, lat: Double, lng: Double) {
    val map = this.map
    launch(CommonPool) {
      val spots = SpotApi().getSpotList(category, lat.toString(), lng.toString())
      println("len=" + spots.spot.size)
      launch(UI) {
        val newList: MutableList<MarkerData> = mutableListOf()
        markerList.forEach {
          it.marker.remove()
        }
        spots.spot.forEach {
          val marker = map?.addMarker(
            MarkerOptions().position(LatLng(it.lat.toDouble(), it.lng.toDouble())).title(it.name))
          if (marker != null) newList.add(MarkerData(spot = it, marker = marker))
        }
        markerList = newList
      }
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == GPS.REQUEST_CODE &&
      grantResults.isNotEmpty() &&
      grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      startGPS()
    } else finish()
  }
}