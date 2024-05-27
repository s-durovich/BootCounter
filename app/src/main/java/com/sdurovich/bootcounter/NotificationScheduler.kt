package com.sdurovich.bootcounter

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationScheduler @Inject constructor(
    private val app: Application
) {

    fun scheduleWithRules() {
        val workRequest =
            PeriodicWorkRequestBuilder<NotificationWorker>(SCHEDULE_TIME_MINUTES, TimeUnit.MINUTES)
                .setInitialDelay(SCHEDULE_TIME_MINUTES, TimeUnit.MINUTES)
                .build()

        WorkManager.getInstance(app).enqueueUniquePeriodicWork(
            NOTIFICATION_TASK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun rescheduleByDismiss() {
        // TODO: Check dismissal count, reschedule notification if needed with conditions
    }

    companion object {
        private const val NOTIFICATION_TASK_NAME = "notification_task"
        private const val SCHEDULE_TIME_MINUTES = 15L
    }
}