package bz.mydns.walt.u_ryo.proplister

import android.app.Application

/**
 * Created by u-ryo on 17/12/04.
 */
class MainApplication: Application() {
    private lateinit var appComponent: AppComponent

    fun getComponent(): AppComponent = appComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .modelModule(ModelModule())
                .build()
    }
}