package com.sdurovich.bootcounter

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.sdurovich.bootcounter.repository.BootCounterRepository
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationManager @Inject constructor(
    private val app: Application,
    private val repository: BootCounterRepository
) {

    private val notificationManager: NotificationManagerCompat by lazy {
        NotificationManagerCompat.from(app)
    }

    private val formatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss", Locale.getDefault())

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                BASE_CHANNEL_ID,
                app.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.setShowBadge(true)
            notificationManager.createNotificationChannels(listOf(channel))
        }
    }

    fun showNotification() {
        val bootInfo = repository.getBootDeviceItems()

        val notification = NotificationCompat.Builder(app, BASE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Reboot info")
            .setContentText(formatMessage(bootInfo))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun formatMessage(rebootItems: List<Long>): String {
        // TODO: Create data mapper
        val items = repository.getBootDeviceItems()
        return when {
            items.isEmpty() -> "No boots detected"
            items.size == 1 -> "The boot was detected = ${formatter.format(rebootItems.first())}"
            else -> {
                val lastBootTime = rebootItems.last()
                val secondLastBootTime = rebootItems[rebootItems.size - 2]
                val timeDiff = lastBootTime - secondLastBootTime
                "Last boots time delta = ${timeDiff}"
            }
        }
    }

    companion object {
        private const val BASE_CHANNEL_ID = "BOOT_COUNTER_BASE_CHANNEL"
        private const val NOTIFICATION_ID = 1
    }
}