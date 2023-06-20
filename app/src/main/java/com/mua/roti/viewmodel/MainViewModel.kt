package com.mua.roti.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.mua.roti.data.datastore.BasicDataStore
import com.mua.roti.model.NotificationEntry
import com.mua.roti.repository.NotificationEntryRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val notificationEntryRepository
            : NotificationEntryRepository
            = NotificationEntryRepository(application)
    val notificationEntries
            : LiveData<MutableList<NotificationEntry>>
            = notificationEntryRepository.notificationEntries

    val _toTop
            : MutableLiveData<Boolean>
            = MutableLiveData(false)
    val toTop
            : LiveData<Boolean> get() = _toTop

    private val basicDataStore = BasicDataStore(application)

    val serviceRunning = basicDataStore.serviceRunning.asLiveData()

    fun setServiceRunning(serviceRunning: Boolean) {
        viewModelScope.launch(IO) {
            basicDataStore.setServiceRunningToStore(serviceRunning)
        }
    }

    init {

    }

}