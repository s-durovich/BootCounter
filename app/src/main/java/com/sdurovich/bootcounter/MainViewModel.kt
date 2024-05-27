package com.sdurovich.bootcounter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.sdurovich.bootcounter.repository.BootCounterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: BootCounterRepository
) : ViewModel() {

    private val _bootInfoData = MutableLiveData(repository.getBootDeviceItems())

    val bootInfoData: LiveData<List<Long>> get() = _bootInfoData
}