package casestudyteam5.it7th.hal.ac.jp.spotin.map

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.service.api.SpotApi
import casestudyteam5.it7th.hal.ac.jp.spotin.service.gps.GPS
import casestudyteam5.it7th.hal.ac.jp.spotin.util.PermissionUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

  var map: GoogleMap? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_map)
    val mapFragment = supportFragmentManager
      .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
    PermissionUtil.requestPermission(this, GPS.PERMISSION, GPS.REQUEST_CODE, "REQUIRE PERMISSION GPS ", "msg" )
  }

  override fun onMapReady(map: GoogleMap?) {
    this.map = map
    map!!.isIndoorEnabled = false
    val tokyo = LatLng(35.681167, 139.767052)
    map.moveCamera(CameraUpdateFactory.newLatLng(tokyo))
    map.moveCamera(CameraUpdateFactory.zoomTo(15f))
    setSpotsToMap("restaulant", "35.681167", "139.767052")
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

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    if (requestCode == GPS.REQUEST_CODE) {
      Toast.makeText(this, (grantResults[0] == 0).toString(), Toast.LENGTH_SHORT).show()
    }
  }
}