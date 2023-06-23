package com.mua.roti.model.builder

import com.mua.roti.model.NotificationEntry

class NotificationEntryBuilder {
    var notificationEntry: NotificationEntry = NotificationEntry()

    fun setKey(key: String?): NotificationEntryBuilder {
        notificationEntry.key = key
        return this
    }

    fun setTitle(title: String?): NotificationEntryBuilder {
        notificationEntry.title = title
        return this
    }

    fun setText(text: String?): NotificationEntryBuilder {
        notificationEntry.text = text
        return this
    }

    fun build(): NotificationEntry {
        return notificationEntry
    }
}