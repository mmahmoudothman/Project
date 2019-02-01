package com.example.mahmoud.architectureexample;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.mahmoud.architectureexample.data.local.SourceRepository;
import com.example.mahmoud.architectureexample.model.Source;

import java.util.List;


public class SourceViewModel extends AndroidViewModel {
    private SourceRepository repository;
    private LiveData<List<Source>> allSources;

    public SourceViewModel(@NonNull Application application) {
        super(application);
        repository = new SourceRepository(application);
        allSources = repository.getAllSources();
    }

    public void insert(Source source) {
        repository.insert(source);
    }


    public void delete(Source source) {
        repository.delete(source);
    }


    public LiveData<List<Source>> getAllSources() {
        return allSources;
    }
}