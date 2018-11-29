package com.example.lmigu.appei;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class ShowEmotionActivity extends AppCompatActivity {


    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_emotion);



        webview = findViewById(R.id.webview);

        webview.loadUrl("https://www.youtube.com/watch?v=fONxIK8KPPA");


    }
}
