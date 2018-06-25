package casestudyteam5.it7th.hal.ac.jp.spotin

import casestudyteam5.it7th.hal.ac.jp.spotin.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

open class App : DaggerApplication() {

  override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
    DaggerAppComponent.builder().create(this)
}
