package com.mua.roti.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mua.roti.dao.NotificationEntryDao;
import com.mua.roti.data.database.ApplicationDatabase;
import com.mua.roti.model.NotificationEntry;

import java.util.List;

public class NotificationEntryRepository {
    private final NotificationEntryDao notificationEntryDao;
    private final LiveData<List<NotificationEntry>> notificationEntries;

    public NotificationEntryRepository(Application application) {
        ApplicationDatabase db = ApplicationDatabase.getInstance(application);
        notificationEntryDao = db.notificationEntryDao();
        notificationEntries = notificationEntryDao.getAll();
    }

    public LiveData<List<NotificationEntry>> getNotificationEntries() {
        return notificationEntries;
    }

    public void insert(NotificationEntry notificationEntry) {
        new InsertAsyncTask(notificationEntryDao).execute(notificationEntry);
    }

    private static class InsertAsyncTask extends AsyncTask<NotificationEntry, Void, Void> {

        private final NotificationEntryDao notificationEntryDao;

        InsertAsyncTask(NotificationEntryDao notificationEntryDao) {
            this.notificationEntryDao = notificationEntryDao;
        }

        @Override
        protected Void doInBackground(final NotificationEntry... params) {
            notificationEntryDao.insert(params[0]);
            return null;
        }
    }
}