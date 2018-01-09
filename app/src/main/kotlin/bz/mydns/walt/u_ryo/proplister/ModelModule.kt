package bz.mydns.walt.u_ryo.proplister

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by u-ryo on 17/12/04.
 */
@Module
class ModelModule {
  @Provides
  @Singleton
  @Named("URL")
  fun provideURL(): String = "https://api.github.com"
}
