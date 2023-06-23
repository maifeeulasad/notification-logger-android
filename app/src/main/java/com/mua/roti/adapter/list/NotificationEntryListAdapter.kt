package com.mua.roti.adapter.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mua.roti.R
import com.mua.roti.adapter.list.NotificationEntryListAdapter.NotificationEntryListViewHolder
import com.mua.roti.model.NotificationEntry
import com.mua.roti.util.ui.DisplayUtil

class NotificationEntryListAdapter : RecyclerView.Adapter<NotificationEntryListViewHolder>() {
    private var notificationEntryList: MutableList<NotificationEntry> = ArrayList()
    private var filteredNotificationEntryList: MutableList<NotificationEntry> = ArrayList()
    private var keyword = ""

    fun search(searchKeyword: String = keyword) {
        filteredNotificationEntryList = ArrayList()
        this.keyword = searchKeyword.trim()
        for (entry in notificationEntryList) {
            if (entry.key != null && entry.key.contains(searchKeyword)) {
                filteredNotificationEntryList.add(entry)
            } else if (entry.title != null && entry.title.contains(searchKeyword)) {
                filteredNotificationEntryList.add(entry)
            } else if (entry.text != null && entry.text.contains(searchKeyword)) {
                filteredNotificationEntryList.add(entry)
            }
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationEntryListViewHolder {
        return NotificationEntryListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_notification_entry, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotificationEntryListViewHolder, position: Int) {
        val entry = filteredNotificationEntryList[position]
        holder.key.text = entry.key
        holder.title.text = entry.title
        holder.text.text = entry.text
        holder.timeStamp.text = entry.timeStamp.toString()

        DisplayUtil.setHighLightedText(holder.key, keyword)
        DisplayUtil.setHighLightedText(holder.title, keyword)
        DisplayUtil.setHighLightedText(holder.text, keyword)
    }

    override fun getItemCount(): Int {
        return filteredNotificationEntryList.size
    }

    fun setNotificationEntryList(notificationEntryList: MutableList<NotificationEntry>) {
        this.notificationEntryList = notificationEntryList
        search()
    }

    inner class NotificationEntryListViewHolder internal constructor(view: View) :
        RecyclerView.ViewHolder(view) {
        val key: TextView
        val title: TextView
        val text: TextView
        val timeStamp: TextView

        init {
            key = view.findViewById(R.id.tv_item_notification_entry_key)
            title = view.findViewById(R.id.tv_item_notification_entry_title)
            text = view.findViewById(R.id.tv_item_notification_entry_text)
            timeStamp = view.findViewById(R.id.tv_item_notification_entry_time_stamp)
        }
    }
}