package casestudyteam5.it7th.hal.ac.jp.spotin.main

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import casestudyteam5.it7th.hal.ac.jp.spotin.map.MapActivity
import casestudyteam5.it7th.hal.ac.jp.spotin.records.TravelRecordListActivity

class MainActivity : FragmentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    to_map.setOnClickListener {
      val intent = Intent(this@MainActivity, MapActivity::class.java)
      startActivity(intent)
    }

    to_list.setOnClickListener {
      val intent = Intent(this@MainActivity, TravelRecordListActivity::class.java)
      startActivity(intent)
    }
  }
}
