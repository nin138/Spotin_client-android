package casestudyteam5.it7th.hal.ac.jp.spotin.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import casestudyteam5.it7th.hal.ac.jp.spotin.data.Spot

@Dao interface SpotDao {
  @Insert
  fun insertSpot(spot: Spot)

  @Insert
  fun insertSpotList(spotlist: List<Spot>)

  @Query("SELECT * FROM Spot")
  fun getAllSpot(): List<Spot>

  @Query ("SELECT * FROM Spot WHERE prace_id = (:prace_id)")
  fun getSpot(prace_id: String): Spot

  @Delete
  fun deletSpot(spot: Spot) //主キーで検索して削除

  //@Query("DELETE FROM Spot where imagepass = imagepass ") //条件
  //fun deleteImageRecord(imagepass : String)
}