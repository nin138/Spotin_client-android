package casestudyteam5.it7th.hal.ac.jp.spotin.main

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.OnMapReadyCallback
import android.support.v4.app.FragmentActivity
import casestudyteam5.it7th.hal.ac.jp.spotin.R

class MainActivity : FragmentActivity(), OnMapReadyCallback {

  private var mMap: GoogleMap? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val mapFragment = supportFragmentManager
      .findFragmentById(R.id.map) as SupportMapFragment
    mapFragment.getMapAsync(this)
  }

  override fun onMapReady(googleMap: GoogleMap) {
    mMap = googleMap

    val tokyo = LatLng(35.681167, 139.767052)
    mMap!!.addMarker(MarkerOptions().position(tokyo).title("Marker in TokyoStation"))
    mMap!!.moveCamera(CameraUpdateFactory.newLatLng(tokyo))
  }
}