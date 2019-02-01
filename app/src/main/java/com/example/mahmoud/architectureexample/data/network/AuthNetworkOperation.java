package com.example.mahmoud.architectureexample.data.network;


import android.util.Log;
import android.widget.Toast;

import com.example.mahmoud.architectureexample.model.FeedModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mahmoud.architectureexample.common.Const.BASE_URL;

@SuppressWarnings({"ConstantConditions", "JavaDoc"})
// *
// * all functions here take listener as a parameter, this listener for make activity implement responses functions to
// * calls like 'onResponse - onFailure - onPostRequestFinished'
// *
// * all have on failure for notify listener that there is a problem with a message as a parameter
// *          on response for notify listener that request done with response
// *          response cast as a object to your model
// *
// * page parameter if this request has pagination
// *
// * the way of this type of communication remove a lot of code from activites and make it simple as possiable
// * functions on response and on failure not more need to be in onCreate when you make a call this 2 functions
// * will be override in each activity need to make request
// *
// * if there is more 1 request you will need to check what type of response come to inflate them in right way to ui
// * or if you need make it more easier and sraight fowrord


public class AuthNetworkOperation {

    private AuthInterface main;

    public AuthNetworkOperation() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .build();
                return chain.proceed(request);
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        main = retrofit.create(AuthInterface.class);
    }


    public void convert(final AuthOnRequestFinishedListener listener, String url) {
        Call<FeedModel> call = main.convertToJson(url);
        call.enqueue(new Callback<FeedModel>() {
            @Override
            public void onResponse(Call<FeedModel> call, Response<FeedModel> response) {
                try {
                    listener.onResponse(response.body());

                }catch (Exception e){
                    Log.e("Response", "Check the Link Provider");

                }

            }

            @Override
            public void onFailure(Call<FeedModel> call, Throwable t) {
                Log.e("Response", t.getMessage() + "");

            }
        });
    }
}
