package com.example.mahmoud.architectureexample.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.mahmoud.architectureexample.model.Source;

import java.util.List;

@Dao
public interface SourceDao {

    @Insert
    void insert(Source source);

    @Delete
    void delete(Source source);

    @Query("SELECT * FROM source_table")
    LiveData<List<Source>> getAllSources();
}
