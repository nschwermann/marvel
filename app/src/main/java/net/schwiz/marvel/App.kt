package net.schwiz.marvel

import android.app.Application
import net.schwiz.marvel.di.appModule
import net.schwiz.marvel.di.dataModule
import net.schwiz.marvel.util.timber
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            timber()
            androidContext(this@App)
            androidFileProperties()
            modules(listOf(appModule, dataModule))
        }
        Timber.plant(getKoin().get())
    }
}