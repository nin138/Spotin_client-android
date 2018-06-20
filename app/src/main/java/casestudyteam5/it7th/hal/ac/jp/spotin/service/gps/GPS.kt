package casestudyteam5.it7th.hal.ac.jp.spotin.service.gps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.support.v4.content.ContextCompat.startActivity
import android.provider.Settings
import android.content.Intent
import android.location.LocationListener
import casestudyteam5.it7th.hal.ac.jp.spotin.util.PermissionUtil

class GPS(private val context: Context) {
  private val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
  companion object {
    const val REQUEST_CODE = 100
    const val PERMISSION = Manifest.permission.ACCESS_FINE_LOCATION
    const val MINIMUM_TIME_INTERVAL_BETWEEN_LOCATION_UPDATES_IN_MILLISEC = 1000L
    const val MINIMUM_DISTANCE_BETWEEN_LOCATION_UPDATES_IN_METERS = 50f
  }
  @SuppressLint("MissingPermission")
  fun startListening(listener: LocationListener) {
    if (!PermissionUtil.isPermissionGranted(context, PERMISSION)) throw Error("permission not granted")
    if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
      val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
      startActivity(context, settingsIntent, null)
    }
    locationManager.requestLocationUpdates(
      LocationManager.GPS_PROVIDER,
      MINIMUM_TIME_INTERVAL_BETWEEN_LOCATION_UPDATES_IN_MILLISEC,
      MINIMUM_DISTANCE_BETWEEN_LOCATION_UPDATES_IN_METERS,
      listener)
  }
}