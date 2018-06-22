package casestudyteam5.it7th.hal.ac.jp.spotin.data.source

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import casestudyteam5.it7th.hal.ac.jp.spotin.data.TravelRecord

//取得データ格納用
class SpotStore{
  @Embedded
  lateinit var travelRecord: TravelRecord

  @Relation(parentColumn = "place_id", entityColumn = "place_id")
  lateinit var spotImageList: List<TravelRecord.SpotImage>
}

