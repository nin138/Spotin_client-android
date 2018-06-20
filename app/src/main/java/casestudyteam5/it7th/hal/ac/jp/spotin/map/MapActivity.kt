package casestudyteam5.it7th.hal.ac.jp.spotin.map

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

  var map: GoogleMap? = null

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
    val tokyo = LatLng(35.681167, 139.767052)
    map.addMarker(MarkerOptions().position(tokyo).title("Marker in TokyoStation"))
    map.moveCamera(CameraUpdateFactory.newLatLng(tokyo))
    map.moveCamera(CameraUpdateFactory.zoomTo(15f))
  }
}