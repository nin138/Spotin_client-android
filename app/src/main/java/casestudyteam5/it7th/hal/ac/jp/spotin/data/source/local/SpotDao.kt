package casestudyteam5.it7th.hal.ac.jp.spotin.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import casestudyteam5.it7th.hal.ac.jp.spotin.data.TravelRecord
import java.util.Date

@Dao interface SpotDao {
  @Insert
  fun insertSpot(travelRecord: TravelRecord)

  @Insert
  fun insertSpotList(travelRecordList: List<TravelRecord>)

  @Insert
  fun addSpotImage(spotimageList: List<TravelRecord.SpotImage>)

  @Query("SELECT * FROM travel_record INNER JOIN spot_image ON travel_record.place_id ")
  fun getAllSpot(): List<TravelRecord>

  @Query ("SELECT * FROM travel_record" +
    "INNER JOIN spot_image ON travel_record.place_id = spot_image.place_id" +
    " WHERE travel_record.place_id = (:place_id)")
  fun getSpotPlace(place_id: String): TravelRecord?

  @Query("SELECT * FROM travel_record INNER JOIN spot_image ON travel_record.place_id WHERE date = (:date)")
  fun getSpotDate(date: Date): List<TravelRecord>

  @Delete
  fun deletSpot(travelRecord: TravelRecord) //主キーで検索して削除

  @Query("DELETE FROM spot_image where image_pass = (:imagepass) ") //条件
  fun deleteSpotImage(imagepass: String)
}