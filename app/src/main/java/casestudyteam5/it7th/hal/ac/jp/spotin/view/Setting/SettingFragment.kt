package casestudyteam5.it7th.hal.ac.jp.spotin.view.Setting

import android.os.Bundle
import android.preference.PreferenceFragment
import casestudyteam5.it7th.hal.ac.jp.spotin.R

class SettingFragment : PreferenceFragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.preferences)
    //TODO: Network,Gpsのpermission処理
  }
}
