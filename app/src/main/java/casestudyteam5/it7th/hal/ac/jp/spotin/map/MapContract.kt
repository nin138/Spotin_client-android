package casestudyteam5.it7th.hal.ac.jp.spotin.map

interface MapContract {
  interface View {
    fun takePicture()//intent
    fun setFavorite()
    //mapの表示
    //スポットの表示
    //スポット選択
    //スポットの詳細(レビュー, 店名,住所)
    //範囲指定
  }
  interface Presenter {
    fun saveSpot()//ピンを立てた場所の一時保存
    fun zoomControl()
    //現在地取得
    //スポット取得
    //お気に入りスポット保存
  }
}