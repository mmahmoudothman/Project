package com.example.mahmoud.architectureexample.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "source_table")
public class Source {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String url;


    public Source(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}