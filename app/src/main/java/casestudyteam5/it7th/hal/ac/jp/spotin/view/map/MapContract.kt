package casestudyteam5.it7th.hal.ac.jp.spotin.view.map

import android.location.LocationListener
import casestudyteam5.it7th.hal.ac.jp.spotin.service.api.entity.Spot
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker

interface MapContract {
  interface View {
    fun updateYouAreHere(location: LatLng)
    fun removeSpotMarkers(markers: List<Marker>)
    fun setSpotMarkers(spots: List<Spot>)
    fun startAddRecordActivity(spotId: String, spotName: String)
//    fun setFavorite()
    // TODO: スポット選択
    // TODO: スポットの詳細(レビュー, 店名,住所)
    // TODO: 範囲指定
  }
  interface Presenter {
    val locationListener: LocationListener
    fun onLocationUpdated(location: LatLng)
    fun onMarkerClicked(marker: Marker)
    fun onMarkerAdded(list: List<MapPresenter.MarkerData>)
    fun onPause()
//    fun zoomControl()
    // TODO: 現在地取得
    // TODO: スポット取得
    // TODO: お気に入りスポット保存
  }
}
