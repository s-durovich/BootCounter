package com.sdurovich.bootcounter

import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.sdurovich.bootcounter.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

//    @Inject
//    lateinit var permissionsManager: PermissionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // TODO: Request permission notification
            // permissionsManager.requestNotificationPermission()
        }

        viewModel.bootInfoData.observe(this) {
            // TODO: Refactor to move data formatting from activity
            binding.counterInfo.text = when {
                it.isEmpty() -> "No boots detected"
                else -> formatDates(it)
            }
        }

        // TODO: Add UI to change dismissal configuration
    }

    private fun formatDates(items: List<Long>): String {
        return items.map {
            Calendar.getInstance().apply { timeInMillis = it }
        }.groupBy {
            it.get(Calendar.DAY_OF_WEEK)
        }.map {
            "${formatter.format(it.value.first().time)} - ${it.value.size}"
        }.joinToString(separator = "\n")
    }
}