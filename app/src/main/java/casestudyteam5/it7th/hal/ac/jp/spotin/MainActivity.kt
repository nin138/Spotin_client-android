package casestudyteam5.it7th.hal.ac.jp.spotin

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import casestudyteam5.it7th.hal.ac.jp.spotin.addrecord.AddRecordActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
      val intent = Intent(this, AddRecordActivity::class.java).apply {
        putExtra("place_id", "place_id")
        putExtra("place_name", "place_name")
        putExtra("update", false)
      }
      startActivity(intent)
    }
}