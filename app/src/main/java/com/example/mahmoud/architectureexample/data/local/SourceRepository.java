package com.example.mahmoud.architectureexample.data.local;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.mahmoud.architectureexample.model.Source;

import java.util.List;

public class SourceRepository {

    private SourceDao sourceDao;
    private LiveData<List<Source>> allSources;

    public SourceRepository(Application application) {
        SourceDatabase database = SourceDatabase.getInstance(application);
        sourceDao = database.sourceDao();
        allSources = sourceDao.getAllSources();

    }


    public void insert(Source source) {
        new InsertSourceAysncTask(sourceDao).execute(source);
    }



    public void delete(Source source) {
        new deleteSourceAysncTask(sourceDao).execute(source);

    }


    public LiveData<List<Source>> getAllSources() {
        return allSources;
    }


    // asyncTasks
    private static class InsertSourceAysncTask extends AsyncTask<Source, Void, Void> {

        private SourceDao sourceDao1;

        private InsertSourceAysncTask(SourceDao sourceDao) {
            this.sourceDao1 = sourceDao;

        }


        @Override
        protected Void doInBackground(Source... sources) {
            sourceDao1.insert(sources[0]);
            return null;
        }
    }


    private static class deleteSourceAysncTask extends AsyncTask<Source, Void, Void> {

        private SourceDao sourceDao1;

        private deleteSourceAysncTask(SourceDao sourceDao) {
            this.sourceDao1 = sourceDao;

        }


        @Override
        protected Void doInBackground(Source... sources) {
            sourceDao1.delete(sources[0]);
            return null;
        }
    }



}
