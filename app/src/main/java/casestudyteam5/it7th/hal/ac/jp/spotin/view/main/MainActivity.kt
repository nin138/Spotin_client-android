package casestudyteam5.it7th.hal.ac.jp.spotin.view.main

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import casestudyteam5.it7th.hal.ac.jp.spotin.view.map.MapActivity

class MainActivity : FragmentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // splash
    try {
      Thread.sleep(2000)
    } catch (e: InterruptedException) {
      e.printStackTrace()
    }

    setTheme(R.style.AppTheme_NoActionBar)
    setContentView(R.layout.activity_main)
    //Map画面へ遷移
    val intent = Intent(this@MainActivity, MapActivity::class.java)
    startActivityForResult(intent, FINISHAPP)
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    //map画面からバックボタン押下でアプリ終了
    if (requestCode == FINISHAPP) { finish() }
  }
  companion object {
      const val FINISHAPP = 3000
  }
}
