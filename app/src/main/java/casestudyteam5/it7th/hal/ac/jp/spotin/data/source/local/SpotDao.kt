package casestudyteam5.it7th.hal.ac.jp.spotin.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Update
import android.arch.persistence.room.Query
import android.arch.persistence.room.Delete
import casestudyteam5.it7th.hal.ac.jp.spotin.data.TravelRecord
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotStore
import java.util.Date

@Dao interface SpotDao {
  @Insert
  fun insertSpot(travelRecord: TravelRecord)

  @Insert
  fun insertSpotList(travelRecordList: List<TravelRecord>)

  @Insert
  fun addSpotImage(spotimageList: List<TravelRecord.SpotImage>)

  @Update
  fun upDateSpot(travelRecord: TravelRecord)

  @Query("SELECT * FROM travel_record")
  fun getAllSpot(): List<SpotStore>

  @Query ("SELECT * FROM travel_record " +
    "INNER JOIN spot_image ON travel_record.place_id = spot_image.place_id " +
    "WHERE travel_record.place_id = (:place_id)")
  fun getSpotPlace(place_id: String): SpotStore?

  @Query("SELECT * FROM travel_record INNER JOIN spot_image ON travel_record.place_id WHERE date = (:date)")
  fun getSpotData(date: Date): List<SpotStore>

  @Delete
  fun deletSpot(travelRecord: TravelRecord) //主キーで検索して削除

  @Query("DELETE FROM spot_image where image_pass = (:imagepass) ") //条件
  fun deleteSpotImage(imagepass: String)
}
