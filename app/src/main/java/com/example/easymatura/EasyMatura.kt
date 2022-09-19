package com.example.easymatura

import android.app.Application
import com.example.easymatura.di.firestoreModule
import com.example.easymatura.di.viewmodelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class EasyMatura: Application() {
    override fun onCreate() {
        super.onCreate()
        application = this

        startKoin{
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@EasyMatura)
            modules(
                firestoreModule,
                viewmodelModule
            )
        }
    }

    companion object{
        lateinit var application:Application
    }
}