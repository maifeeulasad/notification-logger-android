package com.mua.roti.repository;

import static dagger.internal.DoubleCheck.lazy;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mua.roti.dao.NotificationEntryDao;
import com.mua.roti.data.database.ApplicationDatabase;
import com.mua.roti.model.NotificationEntry;

import java.util.List;

import dagger.Lazy;

public class NotificationEntryRepository {
    private static NotificationEntryRepository instance;

    private final NotificationEntryDao notificationEntryDao;
    private final LiveData<List<NotificationEntry>> notificationEntries;

    private NotificationEntryRepository(Application application) {
        Lazy<ApplicationDatabase> databaseLazy = lazy(() -> ApplicationDatabase.getInstance(application));
        notificationEntryDao = databaseLazy.get().notificationEntryDao();
        notificationEntries = notificationEntryDao.getAll();
    }

    public static NotificationEntryRepository getInstance(Application application) {
        if(application==null){
            return instance;
        }
        instance = new NotificationEntryRepository(application);
        return instance;
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
