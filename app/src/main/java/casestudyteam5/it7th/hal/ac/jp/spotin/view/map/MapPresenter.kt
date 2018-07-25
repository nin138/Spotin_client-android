package casestudyteam5.it7th.hal.ac.jp.spotin.view.map

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import android.util.Log
import casestudyteam5.it7th.hal.ac.jp.spotin.service.api.SpotApi
import casestudyteam5.it7th.hal.ac.jp.spotin.service.api.entity.Spot
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.withContext
import javax.inject.Inject

class MapPresenter @Inject constructor(
  private val view: MapActivity,
  private val spotApi: SpotApi
) : MapContract.Presenter {
  data class MarkerData(val spot: Spot, val marker: Marker)
  private var markerList: List<MarkerData> = listOf()
  private var location: LatLng? = null
  private var job: Job? = null

  var selectedCategory = "amusement_park"
    set(category) {
      field = category
      if (location != null) updateSpots(location!!)
    }

  override fun onMarkerAdded(list: List<MarkerData>) {
    markerList = markerList.plus(list)
  }

  override fun onMarkerClicked(marker: Marker) {
    val spot = this.markerList.find { it.marker == marker }
    spot?.let { view.startAddRecordActivity(spotId = it.spot.place_id, spotName = it.spot.name) }
  }

  override fun onLocationUpdated(location: LatLng) {

    view.updateYouAreHere(location)
    this.location = location
    updateSpots(location)
  }

  override fun onPause() {
    job?.cancel()
  }

  private fun updateSpots(location: LatLng) {
    job = launch {
      val spots = spotApi.getSpotList(selectedCategory, location.latitude, location.longitude)
      val markersScheduledForRemoval = mutableListOf<Marker>()
      val markers = markerList.filter {
        if (spots.spot.find { spot -> it.spot.place_id == spot.place_id } == null) {
          markersScheduledForRemoval.add(it.marker)
          false
        } else true
      }
      val newSpots = spots.spot.filter {
        markers.find { markerData -> markerData.spot.place_id == it.place_id } == null
      }
      withContext(UI) {
        if (markersScheduledForRemoval.isNotEmpty()) view.removeSpotMarkers(markersScheduledForRemoval)
        markerList = markers
        if (newSpots.isNotEmpty()) view.setSpotMarkers(newSpots)
      }
    }
  }

  override val locationListener = object : LocationListener {
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
      Log.d("gps::location update", location?.latitude.toString() + "," + location?.longitude)
      if (location != null) onLocationUpdated(LatLng(location.latitude, location.longitude))
    }
  }
}
