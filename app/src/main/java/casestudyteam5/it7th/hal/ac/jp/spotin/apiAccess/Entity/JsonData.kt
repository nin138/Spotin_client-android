package casestudyteam5.it7th.hal.ac.jp.spotin.apiAccess.Entity

class JsonData {
    internal inner class SpotDataList {
        lateinit var list: Map<String, SpotData>
    }

    internal inner class SpotData {
        var lat: Double = 0.toDouble()
        var lng: Double = 0.toDouble()
        var spotId: String? = null
        var spotName: String? = null
        var spotCategory: String? = null
        var spotImgs: List<String>? = null
    }

    internal inner class SpotDetailList {
        lateinit var list: Map<String, SpotDetail>
    }

    internal inner class SpotDetail//TODO スポット詳細のデータクラス
}
