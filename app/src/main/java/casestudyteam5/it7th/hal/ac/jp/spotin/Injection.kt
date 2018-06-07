package casestudyteam5.it7th.hal.ac.jp.spotin

import android.content.Context
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.SpotRepository
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.local.SpotDatabase
import casestudyteam5.it7th.hal.ac.jp.spotin.data.source.local.SpotLocalDataSource
import casestudyteam5.it7th.hal.ac.jp.spotin.util.AppExecutor

//databaseのインスタンス化
object Injection {
  fun provideSpotRepository(context: Context): SpotRepository {
    val database = SpotDatabase.getInstance(context)
    return SpotRepository.getInstance(SpotLocalDataSource.getInstance(database.spotDao(), AppExecutor()))
  }
}