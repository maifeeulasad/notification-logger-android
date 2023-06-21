package com.mua.roti.adapter.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mua.roti.R;
import com.mua.roti.model.NotificationEntry;
import com.mua.roti.util.ui.DisplayUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotificationEntryListAdapter
        extends RecyclerView.Adapter<NotificationEntryListAdapter.NotificationEntryListViewHolder> {
    private List<NotificationEntry> notificationEntryList = new ArrayList<>();
    private List<NotificationEntry> filteredNotificationEntryList = new ArrayList<>();
    private String keyword = "";

    public void search(String keyword) {
        if (Objects.equals(keyword, "")) {
            filteredNotificationEntryList = notificationEntryList;
        } else {
            filteredNotificationEntryList = new ArrayList<>();
            this.keyword = keyword;
            for (NotificationEntry entry : notificationEntryList) {
                if (entry.getKey() != null && entry.getKey().contains(keyword)) {
                    filteredNotificationEntryList.add(entry);
                } else if (entry.getTitle() != null && entry.getTitle().contains(keyword)) {
                    filteredNotificationEntryList.add(entry);
                } else if (entry.getText() != null && entry.getText().contains(keyword)) {
                    filteredNotificationEntryList.add(entry);
                }
            }
        }
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public NotificationEntryListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NotificationEntryListViewHolder((LayoutInflater.from(parent.getContext()))
                .inflate(R.layout.item_notification_entry, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull NotificationEntryListViewHolder holder, int position) {
        NotificationEntry entry = filteredNotificationEntryList.get(position);

        holder.key.setText(entry.getKey());
        holder.title.setText(entry.getTitle());
        holder.text.setText(entry.getText());

        DisplayUtil.setHighLightedText(holder.key, keyword);
        DisplayUtil.setHighLightedText(holder.title, keyword);
        DisplayUtil.setHighLightedText(holder.text, keyword);
    }

    @Override
    public int getItemCount() {
        return filteredNotificationEntryList.size();
    }

    public void setNotificationEntryList(List<NotificationEntry> notificationEntryList) {
        this.notificationEntryList = notificationEntryList;
        notifyDataSetChanged();
    }

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
}