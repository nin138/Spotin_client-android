package casestudyteam5.it7th.hal.ac.jp.spotin.map

import android.location.Location
import android.location.LocationListener
import android.os.Bundle
import casestudyteam5.it7th.hal.ac.jp.spotin.service.api.SpotApi
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class MapPresenter(private val view: MapContract.View) : MapContract.Presenter {

  data class MarkerData(val spot: SpotApi.Spot, val marker: Marker)
  private var markerList: List<MarkerData> = listOf()
  private var location: LatLng? = null
  var selectedCategory = "restaurant"
    set(category) {
      field = category
      if (location != null) setStops(location!!)
    }
  override fun onMarkerAdded(list: List<MarkerData>) {
    markerList = markerList.plus(list)
  }

  override fun onMarkerClicked(marker: Marker) {
    val spot = this.markerList.find { it.marker == marker }!!.spot
    view.startAddRecordActivity(spotId = spot.place_id, spotName = spot.name)
  }

  override fun onLocationUpdated(location: LatLng) {
    view.updateYouAreHere(location)
    this.location = location
    setStops(location)
  }

  private fun setStops(location: LatLng) {
    launch {
      val spots = SpotApi().getSpotList(selectedCategory, location.latitude, location.longitude)

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
      launch(UI) {
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
      println("\n\n\n\n\nlocation: " + location?.latitude.toString() + "," + location?.longitude)
      if (location != null) onLocationUpdated(LatLng(location.latitude, location.longitude))
    }
  }
}
