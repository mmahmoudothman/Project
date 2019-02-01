package com.example.mahmoud.architectureexample.data.local;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.mahmoud.architectureexample.model.Source;

import static com.example.mahmoud.architectureexample.common.Const.MASHABLE;
import static com.example.mahmoud.architectureexample.common.Const.TECHCRUNCH;
import static com.example.mahmoud.architectureexample.common.Const.URL_MASHABLE;
import static com.example.mahmoud.architectureexample.common.Const.URL_TECHCRUNCH;
import static com.example.mahmoud.architectureexample.common.Const.URL_WHITFIN;
import static com.example.mahmoud.architectureexample.common.Const.WHITFIN;

@Database(entities = {Source.class}, version = 1, exportSchema = false)
public abstract class SourceDatabase extends RoomDatabase {

    private static SourceDatabase instance;


    public abstract SourceDao sourceDao();

    public static synchronized SourceDatabase getInstance(Context context) {
        if (instance == null) {
            // init the database
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    SourceDatabase.class, "source_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }


        return instance;
    }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            new PopulateDbAsyncTask(instance).execute();

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {

        private SourceDao sourceDao;

        private PopulateDbAsyncTask(SourceDatabase db) {
            sourceDao = db.sourceDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            sourceDao.insert(new Source(WHITFIN, URL_WHITFIN));
            sourceDao.insert(new Source(MASHABLE, URL_MASHABLE));
            sourceDao.insert(new Source(TECHCRUNCH, URL_TECHCRUNCH));

            return null;
        }
    }

}
