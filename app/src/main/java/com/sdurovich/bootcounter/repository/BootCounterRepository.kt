package com.sdurovich.bootcounter.repository

import android.app.Application
import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BootCounterRepository @Inject constructor(
    private val app: Application
) {
    private val prefs by lazy { app.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    private val bootCountInfo: MutableList<Long>
        get() {
            val numberString = prefs.getString(KEY_BOOT_COUNTER, null)
            return numberString?.split(",")?.map { it.toLong() }?.toMutableList() ?: mutableListOf()
        }

    fun getBootDeviceItems(): List<Long> = bootCountInfo

    fun addNewDeviceBootInfo(timestamp: Long) {
        val items = bootCountInfo
        items.add(timestamp)
        prefs.edit().putString(KEY_BOOT_COUNTER, items.joinToString(",")).apply()
    }

    companion object {
        private const val PREFS_NAME = "user_prefs"

        private const val KEY_BOOT_COUNTER = "boot_counter"
    }
}