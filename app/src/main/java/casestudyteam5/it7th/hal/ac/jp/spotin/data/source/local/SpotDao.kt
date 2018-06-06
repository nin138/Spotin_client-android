package casestudyteam5.it7th.hal.ac.jp.spotin.data.source.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import casestudyteam5.it7th.hal.ac.jp.spotin.data.Spot

@Dao interface SpotDao {
  @Insert
  fun insertRecord(spot: Spot)

  @Insert
  fun insertAll(spotlist: List<Spot>)

  @Query("SELECT * FROM Spot")
  fun record(): List<Spot>

  @Delete
  fun deletRecord(spot: Spot) //主キーで検索して削除

  //@Query("DELETE FROM Spot where imagepass = imagepass ") //条件
  //fun deleteImageRecord(imagepass : String)

}