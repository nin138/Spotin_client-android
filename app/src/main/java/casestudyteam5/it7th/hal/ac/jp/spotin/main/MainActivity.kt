package casestudyteam5.it7th.hal.ac.jp.spotin.main

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import casestudyteam5.it7th.hal.ac.jp.spotin.R
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.util.Log
import casestudyteam5.it7th.hal.ac.jp.spotin.map.MapActivity
import casestudyteam5.it7th.hal.ac.jp.spotin.service.api.SpotApi
import kotlinx.coroutines.experimental.launch

class MainActivity : FragmentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    to_map.setOnClickListener {
      val intent = Intent(this@MainActivity, MapActivity::class.java)
      startActivity(intent)
    }

    val cat = "restaurant"
    val lat = "-33.8670522"
    val lng = "151.1957362"

    launch {
      println(SpotApi().getSpotList(category = cat, lat = lat, lng = lng).spot.map { it.name }.joinToString())
    }
  }
}