package com.example.mahmoud.architectureexample.data.network;


import android.util.Log;

/**
 * listener that we need cross the application to make network calls by sending it in each function
 */

public  class AuthOnRequestFinishedListener<T> {

    public final static String TAG = "AuthOnRequest";

    public void onResponse(T t) {

        Log.e(TAG, "onResponse");

    }


    public void onFailure(String message) {
        try {

        } catch (Exception io) {
            Log.e(TAG, "onFailure" + io.getMessage());
        }
    }



}