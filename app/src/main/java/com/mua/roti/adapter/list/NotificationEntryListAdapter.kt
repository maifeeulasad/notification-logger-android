package com.mua.roti.adapter.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mua.roti.R
import com.mua.roti.common.Util
import com.mua.roti.model.NotificationEntry
import com.mua.roti.util.ui.DisplayUtil

class NotificationEntryListAdapter :
    RecyclerView.Adapter<NotificationEntryListAdapter.NotificationEntryListViewHolder>() {

    private var notificationEntryList: List<NotificationEntry> = emptyList()
    private var filteredNotificationEntryList: List<NotificationEntry> = emptyList()
    private var keyword = ""

    fun getFilterSizeAndTotalSize(): Pair<Int, Int> =
        filteredNotificationEntryList.size to notificationEntryList.size

    fun search(searchKeyword: String = keyword) {
        filteredNotificationEntryList = notificationEntryList.filter { entry ->
            entry.key?.contains(searchKeyword, ignoreCase = true) == true ||
                    entry.title?.contains(searchKeyword, ignoreCase = true) == true ||
                    entry.text?.contains(searchKeyword, ignoreCase = true) == true
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
        holder.bind(entry, keyword)
    }

    override fun getItemCount(): Int = filteredNotificationEntryList.size

    fun setNotificationEntryList(notificationEntryList: List<NotificationEntry>) {
        this.notificationEntryList = notificationEntryList
        search()
    }

    inner class NotificationEntryListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val key: TextView = view.findViewById(R.id.tv_item_notification_entry_key)
        private val title: TextView = view.findViewById(R.id.tv_item_notification_entry_title)
        private val text: TextView = view.findViewById(R.id.tv_item_notification_entry_text)
        private val timeStamp: TextView = view.findViewById(R.id.tv_item_notification_entry_time_stamp)

        fun bind(entry: NotificationEntry, keyword: String) {
            key.text = entry.key
            title.text = entry.title
            text.text = entry.text
            timeStamp.text = Util.getTimeString(entry.timeStamp)

            DisplayUtil.setHighLightedText(key, keyword)
            DisplayUtil.setHighLightedText(title, keyword)
            DisplayUtil.setHighLightedText(text, keyword)
        }
    }
}
