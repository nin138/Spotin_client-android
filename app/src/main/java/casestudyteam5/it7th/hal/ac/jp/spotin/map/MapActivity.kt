package casestudyteam5.it7th.hal.ac.jp.spotin.map

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.service.api.SpotApi
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import android.location.LocationManager
import android.widget.Toast
import android.content.pm.PackageManager
import android.content.Context
import android.location.Location
import android.support.v4.content.PermissionChecker
import android.location.Criteria
import android.support.v4.app.ActivityCompat
import android.content.DialogInterface
import android.location.LocationListener
import android.support.v7.app.AlertDialog

class MapActivity :
        AppCompatActivity(),
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener {

  var map: GoogleMap? = null
  private val MY_LOCATION_REQUEST_CODE = 1000
  var myLocationManager: LocationManager? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map)
    val mapFragment = supportFragmentManager
      .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
  }

  override fun onMapReady(map: GoogleMap?) {
    this.map = map
    map!!.isIndoorEnabled = false

    if (PermissionChecker.checkSelfPermission(this,
        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
      myLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
      val provider = getProvider()
      val lastLocation = myLocationManager!!.getLastKnownLocation(provider)
      if (lastLocation != null) {
        setLocation(lastLocation)
      }
      map.setMyLocationEnabled(true)
      Toast.makeText(this, "Provider=$provider", Toast.LENGTH_SHORT).show()
      myLocationManager!!.requestLocationUpdates(provider, 0, 0f, this)
    } else {
      setDefaultLocation()
      confirmPermission()
    }

    val tokyo = LatLng(35.681167, 139.767052)
    map.moveCamera(CameraUpdateFactory.newLatLng(tokyo))
    map.moveCamera(CameraUpdateFactory.zoomTo(15f))
    setSpotsToMap("restaulant", "35.681167", "139.767052")
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
    if (requestCode == MY_LOCATION_REQUEST_CODE) {
      if (PermissionChecker.checkSelfPermission(this,
          Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        map!!.setMyLocationEnabled(true)
        myLocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        myLocationManager!!.requestLocationUpdates(getProvider(), 0, 0f, this)
      } else {
        Toast.makeText(this, "権限を取得できませんでした。", Toast.LENGTH_SHORT).show()
      }
    }
  }

  override fun onLocationChanged(location: Location) {
    Toast.makeText(this, "LocationChanged実行", Toast.LENGTH_SHORT).show()
    setLocation(location)
    try {
      myLocationManager!!.removeUpdates(this)
    } catch (e: SecurityException) {
      Toast.makeText(this, "例外", Toast.LENGTH_SHORT).show()
    }
  }

  override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}

  override fun onProviderEnabled(s: String) {}

  override fun onProviderDisabled(s: String) {}

  public override fun onDestroy() {
    super.onDestroy()
    try {
      myLocationManager!!.removeUpdates(this)
    } catch (e: SecurityException) {
      Toast.makeText(this, "例外", Toast.LENGTH_SHORT).show()
    }
  }

  private fun getProvider(): String {
    val criteria = Criteria()
    return myLocationManager!!.getBestProvider(criteria, true)
  }

  private fun confirmPermission() {
    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
      AlertDialog.Builder(this).setTitle("パーミッション説明")
        .setMessage("このアプリを実行するには位置情報の権限を与えてやる必要です。よろしくお願い致します。")
        .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialog, which ->
          ActivityCompat.requestPermissions(this@MapActivity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            MY_LOCATION_REQUEST_CODE)
        })
        .create()
        .show()
    } else {
      ActivityCompat.requestPermissions(this@MapActivity,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
        MY_LOCATION_REQUEST_CODE)
    }
  }

  private fun setDefaultLocation() {
    val tokyo = LatLng(35.681298, 139.766247)
    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(tokyo, 18f))
  }

  private fun setLocation(location: Location) {
    val map = this.map
    val lat = location.latitude.toString()
    val lng = location.longitude.toString()

    val myLocation = LatLng(location.latitude, location.longitude)
    map?.addMarker(MarkerOptions().position(myLocation).title("now Location"))
    map?.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 18f))

    launch(UI) {
      val place = SpotApi().getSpotList("restaulant", lat, lng)
      place.spot.forEach {
        map?.addMarker(MarkerOptions().position(LatLng(it.lat.toDouble(), it.lng.toDouble())).title(it.name))
      }
    }
  }

  private fun setSpotsToMap(category: String, lat: String, lng: String) {
    val map = this.map
    launch(UI) {
      val spots = SpotApi().getSpotList(category, lat, lng)
      spots.spot.forEach {
        map?.addMarker(MarkerOptions().position(LatLng(it.lat.toDouble(), it.lng.toDouble())).title(it.name))
      }
    }
  }
}