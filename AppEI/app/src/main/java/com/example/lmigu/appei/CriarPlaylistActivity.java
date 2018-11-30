package com.example.lmigu.appei;

import android.app.Activity;
import android.content.Intent;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class CriarPlaylistActivity extends AppCompatActivity {


    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> listEmotion = new ArrayList<>();

    Playlist playlist = GlobalVars.playlist;

    private static final int REQ_CODE_PICK_SOUNDFILE = 1;
    WebView webview;
    Button btnGoToYoutube;
    ImageButton btnHappy;
    ImageButton btnSad;
    ImageButton btnNeutral;
    ImageButton btnAngry;
    Uri audioFileUri;
    Spinner spinnerMusicas, spinnerMusicas2,spinnerMusicas3,spinnerMusicas4,spinnerMusicas5,spinnerMusicas6,spinnerMusicas7,spinnerMusicas8,spinnerMusicas9,spinnerMusicas10;
    public static Integer[] imageEmoji = {R.drawable.happy_emoji,R.drawable.neutral_emoji,R.drawable.sad_emoji};
    public static  String [] imageEmojiString = {"happy","neutral","sad"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_playlist);
        webview = findViewById(R.id.webview);
        //btn to youtube and find new new playlist
        criarViewGoYoutube();


        //receber dados do youtube e guardar no array das musicas
        getYoutubeUrls();

    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        startActivity(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        finish();
//
//
//    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void criarViewGoYoutube(){

        btnGoToYoutube = findViewById(R.id.btn_goToYoutube);
        btnGoToYoutube.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                webview.loadUrl("https://www.youtube.com/");
            }
        });


    }
    public void onBackPressed(){
        if (webview.isFocused() && webview.canGoBack()) {
            webview.goBack();
        }
        else {
            super.onBackPressed();
        }

    }
    public void getYoutubeUrls(){
        Bundle extras = getIntent().getExtras();

        if(extras!=null) {
            String urlReceived = extras.getString(Intent.EXTRA_TEXT);
            String videoTitle = extras.getString(Intent.EXTRA_SUBJECT).split(Character.toString((char)24))[0];
            String videoTitle2 = videoTitle.split(" ")[0];
            String videoTitle3 = videoTitle.split(videoTitle2)[1];
            int i = videoTitle.split(" ").length;
            String videoTitleFinal = videoTitle3.split(videoTitle.split(" ")[i-2])[0];

            //adicionar ao array principal das musicas
            GlobalVars.urlsYoutube.add(urlReceived);

            //adicionar á playlist
            playlist.append(urlReceived);

            System.out.println(playlist.getPlaylist());
            System.out.println(videoTitle);
            playlist.getPlaylist();

        }

        webview.clearFormData();
    }


}
