package casestudyteam5.it7th.hal.ac.jp.spotin.util

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor

//非同期処理(データベース、ネットワーク)
//データベース処理をする際はlocalExecutorのexecute{}に処理を記述
//UIに関わる処理(データの取得表示等)はmainThreadのexecute{}に処理を記述
open class AppExecutor constructor(
  val localExecutor: Executor = LocalExecutor(),
  val mainThread: Executor = MainThreadExecutor()
) {
  private class MainThreadExecutor : Executor {
    private val mainThreadHandler = Handler(Looper.getMainLooper())
    override fun execute(command: Runnable) {
      mainThreadHandler.post(command)
    }
  }
}