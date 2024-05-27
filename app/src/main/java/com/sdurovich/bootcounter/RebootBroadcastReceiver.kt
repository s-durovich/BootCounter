package com.sdurovich.bootcounter

import android.content.Context
import android.content.Intent
import com.sdurovich.bootcounter.repository.BootCounterRepository
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class RebootBroadcastReceiver : HiltBroadcastReceiver() {

    @Inject
    lateinit var userPrefs: BootCounterRepository

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != Intent.ACTION_BOOT_COMPLETED) return

        Timber.d("Received boot complete event.")
        userPrefs.addNewDeviceBootInfo(System.currentTimeMillis())
    }
}