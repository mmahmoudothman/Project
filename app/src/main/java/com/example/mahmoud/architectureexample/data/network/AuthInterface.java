package com.example.mahmoud.architectureexample.data.network;



import com.example.mahmoud.architectureexample.model.FeedModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AuthInterface {

    /**
     * Result endpoint methods
     */



//    https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Ftechcrunch.com%2Ffeed%2F
//  base on the Same Response of Rss Fees i Create a general model {@FeedModel}

    @GET(Routes.URL)
    Call<FeedModel> convertToJson(@Query("rss_url") String url);




}
