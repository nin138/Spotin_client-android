package casestudyteam5.it7th.hal.ac.jp.spotin.map

interface MapContract {
  interface View {
    fun takePicture()//intent
    fun setFavorite()
    // TODO: mapの表示
    // TODO: スポットの表示
    // TODO: スポット選択
    // TODO: スポットの詳細(レビュー, 店名,住所)
    // TODO: 範囲指定
  }
  interface Presenter {
    fun saveSpot()//ピンを立てた場所の一時保存
    fun zoomControl()
    // TODO: 現在地取得
    // TODO: スポット取得
    // TODO: お気に入りスポット保存
  }
}
