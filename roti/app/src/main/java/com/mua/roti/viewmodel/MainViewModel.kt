package com.mua.roti.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.mua.roti.model.NotificationEntry
import com.mua.roti.repository.NotificationEntryRepository

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val notificationEntryRepository
            : NotificationEntryRepository
            = NotificationEntryRepository(application)
    val notificationEntries
            : LiveData<MutableList<NotificationEntry>>
            = notificationEntryRepository.notificationEntries

}