package com.sdurovich.bootcounter

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber

@HiltWorker
class NotificationWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val notificationManager: NotificationManager
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        Timber.d("Show notification")
        notificationManager.showNotification()
        return Result.success()
    }
}