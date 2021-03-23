package com.mua.roti


import androidx.activity.viewModels
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
import android.text.TextUtils
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mua.roti.adapter.NotificationEntryListAdapter
import com.mua.roti.databinding.ActivityMainBinding
import com.mua.roti.service.NotificationListener
import com.mua.roti.viewmodel.MainViewModel
import com.mua.roti.viewmodel.viewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var notificationsRecyclerView : RecyclerView
    private lateinit var scrollToTopButton : Button
    private lateinit var mBinding : ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        viewModelFactory { MainViewModel(this.application) }
    }
    private lateinit var notificationListAdapter : NotificationEntryListAdapter
    private lateinit var layoutManager : LinearLayoutManager



    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initViewModelAndBinding()
        initPermissions()
        initView()
        startNotificationListener()
    }

    private fun startNotificationListener(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2
            && isNotificationServiceEnabled()) {
            startService(Intent(this,NotificationListener::class.java))
        }
    }

    private fun initViewModelAndBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel
        mBinding.main = viewModel
        mBinding.lifecycleOwner = this
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP_MR1)
    private fun initPermissions(){
        if (!isNotificationServiceEnabled()) {
            startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }
    }

    private fun initView(){
        notificationsRecyclerView = mBinding.rvNotifications
        scrollToTopButton = mBinding.btnScrollToTop

        initScrollToTop()
        initRvData()
    }

    private fun initScrollToTop(){
        scrollToTopButton.setOnClickListener {
            layoutManager.scrollToPositionWithOffset(viewModel.notificationEntries.value!!.size,0)
            viewModel._toTop.value = false
        }
    }

    private fun initRvData(){
        notificationListAdapter = NotificationEntryListAdapter()
        notificationsRecyclerView.adapter = notificationListAdapter

        layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        notificationsRecyclerView.layoutManager = layoutManager

        viewModel.notificationEntries.observe(mBinding.lifecycleOwner!!, Observer {
            notificationListAdapter.setNotificationEntryList(it)
            viewModel._toTop.value = true
        })
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat: String = Settings.Secure.getString(contentResolver,
                "enabled_notification_listeners")
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":").toTypedArray()
            for (name in names) {
                val cn = ComponentName.unflattenFromString(name)
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }

}