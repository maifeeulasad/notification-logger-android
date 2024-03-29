package com.mua.roti.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.mua.roti.data.datastore.BasicDataStore
import com.mua.roti.model.NotificationEntry
import com.mua.roti.repository.NotificationEntryRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val notificationEntryRepository: NotificationEntryRepository = NotificationEntryRepository.getInstance(application)
    val notificationEntries: LiveData<MutableList<NotificationEntry>> = notificationEntryRepository.notificationEntries

    private val basicDataStore = BasicDataStore(application)

    val serviceRunning = basicDataStore.serviceRunning.asLiveData()
    val serviceRunningText = MediatorLiveData("")
    val searchKeyword = MutableLiveData("")

    val filterSizeAndTotalSize = MutableLiveData(Pair(0, 0))
    val filterText = MediatorLiveData("")
    val filterTextVisibility = MediatorLiveData(false)

    fun setServiceRunning(serviceRunning: Boolean) {
        viewModelScope.launch(IO) {
            basicDataStore.setServiceRunningToStore(serviceRunning)
        }
    }

    init {
        serviceRunningText.addSource(serviceRunning) { isRunning ->
            serviceRunningText.value =
                if (isRunning) "Service is running" else "Service is NOT running"
        }
        filterText.addSource(filterSizeAndTotalSize) {
            filterText.value =
                "Matched " + it.first + " record(s); out of " + it.second + " record(s);"
        }
        filterTextVisibility.addSource(filterSizeAndTotalSize) {
            filterTextVisibility.value = it.first != it.second
        }
    }

}