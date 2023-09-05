package com.mua.roti

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mua.roti.adapter.list.NotificationEntryListAdapter
import com.mua.roti.databinding.ActivityMainBinding
import com.mua.roti.notification.NotificationHost
import com.mua.roti.service.EntryService
import com.mua.roti.viewmodel.MainViewModel
import com.mua.roti.viewmodel.viewModelFactory
import com.mua.roti.worker.ServiceBootWorker
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private lateinit var notificationsRecyclerView: RecyclerView
    private lateinit var mBinding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory { MainViewModel(this.application) }
    }
    private lateinit var notificationListAdapter: NotificationEntryListAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private val searchDebounceHandler = Handler(Looper.getMainLooper())
    private val searchDebounceRunnable = Runnable {
        viewModel.searchKeyword.value?.let { notificationListAdapter.search(it) }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModelAndBinding()
        initPermissions()
        initView()
        startService()
        startWorker()
    }

    private fun startService() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
            && isNotificationServiceEnabled()
        ) {
            startService(Intent(this, EntryService::class.java))
        }
    }

    private fun startWorker() {
        val workRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<ServiceBootWorker>(15, TimeUnit.MINUTES).build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "notification-listener--service-runner",
            ExistingPeriodicWorkPolicy.UPDATE, workRequest
        )
    }

    private fun initViewModelAndBinding() {
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel
        mBinding.main = viewModel
        mBinding.lifecycleOwner = this

        mBinding.btnTest.setOnClickListener {
            viewModel.setServiceRunning(!viewModel.serviceRunning.value!!)
        }
        mBinding.btnPing.setOnClickListener {
            NotificationHost.showDummyNotification(this, intent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private fun initPermissions() {
        if (!isNotificationServiceEnabled()) {
            startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
    }

    private fun initView() {
        notificationsRecyclerView = mBinding.rvNotifications

        initRvData()
    }

    private fun initRvData() {
        notificationListAdapter = NotificationEntryListAdapter()
        notificationsRecyclerView.adapter = notificationListAdapter

        layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        notificationsRecyclerView.layoutManager = layoutManager

        viewModel.notificationEntries.observe(mBinding.lifecycleOwner!!) {
            notificationListAdapter.setNotificationEntryList(it)
        }

        viewModel.searchKeyword.observe(mBinding.lifecycleOwner!!) {
            searchDebounceHandler.removeCallbacks(searchDebounceRunnable)
            searchDebounceHandler.postDelayed(searchDebounceRunnable, 300)
        }

        notificationListAdapter.registerAdapterDataObserver(object : AdapterDataObserver() {
            override fun onChanged() {
                viewModel.filterSizeAndTotalSize.postValue(notificationListAdapter.getFilterSizeAndTotalSize())
            }
        })
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat: String = Settings.Secure.getString(
            contentResolver,
            "enabled_notification_listeners"
        )
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":").toTypedArray()
            for (name in names) {
                val cn = ComponentName.unflattenFromString(name)
                if (cn != null && TextUtils.equals(pkgName, cn.packageName)) {
                    return true
                }
            }
        }
        return false
    }
}
