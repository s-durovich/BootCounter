package com.sdurovich.bootcounter

import android.content.Context
import android.content.Intent
import com.sdurovich.bootcounter.repository.BootCounterRepository
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class NotificationDismissedReceiver : HiltBroadcastReceiver() {

    @Inject
    lateinit var userPrefs: BootCounterRepository

    @Inject
    lateinit var scheduler: NotificationScheduler

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != "android.intent.action.NOTIFICATION_CANCELLED") return

        Timber.d("Notification dismiss.")
        // TODO: Handle notification dismissal
        // TODO: Increment dismissal count
        scheduler.rescheduleByDismiss()
    }
}