package com.sdurovich.bootcounter

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.Lazy
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var scheduler: NotificationScheduler

    @Inject
    lateinit var workerFactoryLazy: Lazy<HiltWorkerFactory>

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        scheduler.scheduleWithRules()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactoryLazy.get())
            .build()
}