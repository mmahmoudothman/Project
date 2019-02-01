package com.example.mahmoud.architectureexample;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dmax.dialog.SpotsDialog;

import static com.example.mahmoud.architectureexample.common.Const.WEB_URL;

public class DetailArticle extends AppCompatActivity {


    WebView webView;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_article);


        dialog = new SpotsDialog(this);
        dialog.show();
        //WebView
        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient() {

            //press Ctrl+O

            @Override
            public void onPageFinished(WebView view, String url) {
                dialog.dismiss();
            }
        });

        if (getIntent() != null) {
            if (!getIntent().getStringExtra(WEB_URL).isEmpty())
                webView.loadUrl(getIntent().getStringExtra(WEB_URL));
        }


    }
}
