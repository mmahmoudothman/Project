package com.example.mahmoud.architectureexample;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahmoud.architectureexample.adapter.ListNewsAdapter;
import com.example.mahmoud.architectureexample.data.network.AuthNetworkOperation;
import com.example.mahmoud.architectureexample.data.network.AuthOnRequestFinishedListener;
import com.example.mahmoud.architectureexample.model.FeedModel;
import com.example.mahmoud.architectureexample.model.Item;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.github.florent37.diagonallayout.DiagonalLayout;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;

import static com.example.mahmoud.architectureexample.common.Const.URL_EXTRA;
import static com.example.mahmoud.architectureexample.common.Const.URL_TITLE;
import static com.example.mahmoud.architectureexample.common.Const.WEB_URL;

public class ListNews extends AppCompatActivity {

    KenBurnsView kbv;
    DiagonalLayout diagonalLayout;
    AlertDialog dialog;
    TextView top_author, top_title;
    SwipeRefreshLayout swipeRefreshLayout;
    ListNewsAdapter adapter;
    RecyclerView lstNews;
    RecyclerView.LayoutManager layoutManager;
    String webHotURL = "";
    AuthNetworkOperation authNetworkOperation;
    static String urlArticle = "";
    static String urlTitle = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        //Init cache
        Paper.init(this);

        dialog = new SpotsDialog(this);
        authNetworkOperation = new AuthNetworkOperation();
        // bind data
        //View
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNews(true);
            }
        });

        diagonalLayout = (DiagonalLayout) findViewById(R.id.diagonalLayout);
        diagonalLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(getBaseContext(), DetailArticle.class);
                detail.putExtra(WEB_URL, webHotURL);
                startActivity(detail);
            }
        });
        kbv = (KenBurnsView) findViewById(R.id.top_image);
        top_author = (TextView) findViewById(R.id.top_author);
        top_title = (TextView) findViewById(R.id.top_title);

        lstNews = (RecyclerView) findViewById(R.id.lstNews);
        lstNews.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        lstNews.setLayoutManager(layoutManager);


        // get data from intent
        if (getIntent() != null) {
            urlArticle = getIntent().getStringExtra(URL_EXTRA);
            urlTitle = getIntent().getStringExtra(URL_TITLE);

//            feedModel = (FeedModel) getIntent().getSerializableExtra(Intent.EXTRA_TEXT);
//            url = feedModel.getFeed().getUrl();`
            loadNews(false);

        }
    }


    private void loadNews(boolean isRefreshed) {


        if (!isRefreshed) { // first time
            if (isNetworkConnected()) {
                // if there is interenet connection
                dialog.show();
                if (!urlArticle.equals("")) {
                    authNetworkOperation.convert(auth, urlArticle);
                }

            } else {
                // if there is not interenet connection
                String cache = Paper.book().read(urlTitle);
                if (cache != null && !cache.isEmpty() && !cache.equals("null")) // If have cache
                {

                    FeedModel model = new Gson().fromJson(cache, FeedModel.class); // Convert cache from Json to Object
                    setData(model);

                }
            }


        } else {
            dialog.show();

            if (!urlArticle.equals("")) {
                authNetworkOperation.convert(auth, urlArticle);
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    AuthOnRequestFinishedListener auth = new AuthOnRequestFinishedListener() {
        @Override
        public void onResponse(Object object) {
            super.onResponse(object);
            dialog.dismiss();

            try {
                FeedModel model = (FeedModel) object;
                setData(model);
            } catch (Exception e) {
                Toast.makeText(ListNews.this, "Check the Link Provider", Toast.LENGTH_LONG).show();
            }


        }


        @Override
        public void onFailure(String message) {
            super.onFailure(message);
            Log.e("Response", message + "");
            Toast.makeText(ListNews.this, "Check the connectioin", Toast.LENGTH_LONG).show();


        }
    };


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    private void setData(FeedModel data) {

        FeedModel model = data;

        String thumbnailUrl = model.getItems().get(0).getThumbnail();
        //Get first article
        if (!thumbnailUrl.equals("")) {
            Picasso.get()
                    .load(thumbnailUrl)
                    .into(kbv);

        }

        top_title.setText(model.getItems().get(0).getTitle());
        top_author.setText(model.getItems().get(0).getAuthor());

        webHotURL = model.getItems().get(0).getLink();

        //Load remain articles
        List<Item> removeFristItem = model.getItems();
        //Because we already load first item to show on Diagonal Layout
        //So we need remove it
        removeFristItem.remove(0);
        adapter = new ListNewsAdapter(removeFristItem, getBaseContext());
        adapter.notifyDataSetChanged();
        lstNews.setAdapter(adapter);


        //Save to cache
        Paper.book().write(urlTitle, new Gson().toJson(model));

    }
}
