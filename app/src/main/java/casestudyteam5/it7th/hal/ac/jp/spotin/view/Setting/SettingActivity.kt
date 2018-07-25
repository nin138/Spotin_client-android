package casestudyteam5.it7th.hal.ac.jp.spotin.view.Setting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class SettingActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    fragmentManager.beginTransaction()
      .replace(android.R.id.content, SettingFragment())
      .commit()
  }
}
