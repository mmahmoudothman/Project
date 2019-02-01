
package com.example.mahmoud.architectureexample.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class Enclosure implements Serializable {

    @SerializedName("link")
    private String mLink;

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

}
