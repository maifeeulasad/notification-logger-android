package com.mua.roti.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mua.roti.R;
import com.mua.roti.model.NotificationEntry;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NotificationEntryListAdapter
        extends RecyclerView.Adapter<NotificationEntryListAdapter.NotificationEntryListViewHolder> {
    private List<NotificationEntry> notificationEntryList = new ArrayList<>();


    protected class NotificationEntryListViewHolder extends RecyclerView.ViewHolder {
        private final TextView key;
        private final TextView title;
        private final TextView text;
        private final TextView timeStamp;

        NotificationEntryListViewHolder(View view) {
            super(view);
            key = view.findViewById(R.id.tv_item_notification_entry_key);
            title = view.findViewById(R.id.tv_item_notification_entry_title);
            text = view.findViewById(R.id.tv_item_notification_entry_text);
            timeStamp = view.findViewById(R.id.tv_item_notification_entry_time_stamp);
        }
    }

    @NotNull
    @Override
    public NotificationEntryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationEntryListViewHolder((LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.item_notification_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull NotificationEntryListViewHolder holder, int position) {
        holder.key.setText(notificationEntryList.get(position).getKey());
        holder.title.setText(notificationEntryList.get(position).getTitle());
        holder.text.setText(notificationEntryList.get(position).getText());
        holder.timeStamp.setText(notificationEntryList.get(position).getTimeStamp().toString());
    }

    @Override
    public int getItemCount() {
        return notificationEntryList.size();
    }

    public void setNotificationEntryList(List<NotificationEntry> notificationEntryList) {
        this.notificationEntryList = notificationEntryList;
        notifyDataSetChanged();
    }
}