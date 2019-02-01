//package com.example.mahmoud.architectureexample.worker;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.content.Context;
//import android.support.annotation.NonNull;
//import android.support.v4.app.NotificationCompat;
//import android.util.Log;
//
//import com.example.mahmoud.architectureexample.R;
//import com.example.mahmoud.architectureexample.data.network.AuthNetworkOperation;
//import com.example.mahmoud.architectureexample.data.network.AuthOnRequestFinishedListener;
//import com.example.mahmoud.architectureexample.model.FeedModel;
//import com.google.gson.Gson;
//
//import androidx.work.Data;
//import androidx.work.Worker;
//
//public class MyWorker extends Worker {
//    AuthNetworkOperation authNetworkOperation;
//    public static final String EXTRA_TITLE = "title";
//    public static final String EXTRA_OUTPUT_MESSAGE = "output_message";
//    String outputStr;
//
//    @NonNull
//    @Override
//    public Result doWork() {
//
//        authNetworkOperation = new AuthNetworkOperation();
//        String url = getInputData().getString(EXTRA_TITLE, "");
//
//
//        if (url.isEmpty() || url.equals("")) {
//            authNetworkOperation.convert(auth, url);
//        }
//
//        return Result.SUCCESS;
//    }
//
//
//    AuthOnRequestFinishedListener auth = new AuthOnRequestFinishedListener() {
//        @Override
//        public void onResponse(Object object) {
//            super.onResponse(object);
//
//            try {
//                FeedModel model = (FeedModel) object;
//                outputStr = new Gson().toJson(model);
//
//                Data output = new Data.Builder()
//                        .putString(EXTRA_OUTPUT_MESSAGE, "I have come from MyWorker!")
//                        .build();
//                setOutputData(output);
//
//
//            } catch (Exception e) {
//            }
//
//
//        }
//
//
//        @Override
//        public void onFailure(String message) {
//            super.onFailure(message);
//            Log.e("Response", message + "");
//        }
//    };
//
//
//    public void sendNotification(String title, String message) {
//        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
//
//        //If on Oreo then notification required a notification channel.
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "default")
//                .setContentTitle(title)
//                .setContentText(message)
//                .setSmallIcon(R.mipmap.ic_launcher);
//
//        notificationManager.notify(1, notification.build());
//    }
//}