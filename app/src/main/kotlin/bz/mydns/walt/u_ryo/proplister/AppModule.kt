package bz.mydns.walt.u_ryo.proplister

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * Created by u-ryo on 17/12/04.
 */
@Module
class AppModule(private val application: Application) {
    @Provides
    fun provideContext(): Context = application
}