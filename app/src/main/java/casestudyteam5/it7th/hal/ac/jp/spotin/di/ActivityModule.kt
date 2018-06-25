package casestudyteam5.it7th.hal.ac.jp.spotin.di

import casestudyteam5.it7th.hal.ac.jp.spotin.view.map.MapActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {

  @ContributesAndroidInjector
  fun contributeMapActivity(): MapActivity
}
