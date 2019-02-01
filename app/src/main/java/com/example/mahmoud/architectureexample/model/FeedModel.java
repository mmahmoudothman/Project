
package com.example.mahmoud.architectureexample.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class FeedModel implements Serializable {

    @SerializedName("feed")
    private Feed mFeed;
    @SerializedName("items")
    private List<Item> mItems;
    @SerializedName("status")
    private String mStatus;

    public Feed getFeed() {
        return mFeed;
    }

    public void setFeed(Feed feed) {
        mFeed = feed;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
