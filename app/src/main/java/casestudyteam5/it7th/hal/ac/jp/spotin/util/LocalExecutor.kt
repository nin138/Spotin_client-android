package casestudyteam5.it7th.hal.ac.jp.spotin.util

import java.util.concurrent.Executor
import java.util.concurrent.Executors

//ローカルデータベース用非同期処理クラス
class LocalExecutor : Executor {
  //スレッドの生成
  val createThread = Executors.newSingleThreadExecutor()
  override fun execute(command: Runnable?) { createThread.execute(command) }
}