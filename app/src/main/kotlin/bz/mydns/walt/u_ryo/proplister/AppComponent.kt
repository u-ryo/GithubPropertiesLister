package bz.mydns.walt.u_ryo.proplister

import dagger.Component
import javax.inject.Singleton

/**
 * Created by u-ryo on 17/12/04.
 */
@Singleton
@Component(modules = [AppModule::class, ModelModule::class])
interface AppComponent {
  fun inject(activity: MainActivity)
}
