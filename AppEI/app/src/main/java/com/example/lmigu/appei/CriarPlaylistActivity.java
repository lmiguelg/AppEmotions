package com.example.lmigu.appei;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.microedition.khronos.opengles.GL;

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
    int width;
    private String selecteditem;
    TableLayout tableMusicEmotion;
    public static  String [] imageEmojiString = {"","happy","neutral","sad"};
    public Map<String,String> mapMusicEmotion = GlobalVars.mapMusicEmotion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_playlist);
        try{


        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        width = displaymetrics.widthPixels;
        int width = displaymetrics.widthPixels;
        webview = findViewById(R.id.webview);
        tableMusicEmotion = findViewById(R.id.tableMusicEmotion);
        criaTabelaMusicas();
        //btn to youtube and find new new playlist
        criarViewGoYoutube();


        //receber dados do youtube e guardar no array das musicas e no storage
        getYoutubeUrls();
        }catch(Exception e){

        }

    }
    public void criaTabelaMusicas(){


        for(int i = 0; i < playlist.getMapPlaylist().size(); i++){
            TableRow newRow = new TableRow(this);
            TextView txt = new TextView(this);
            txt.setText(playlist.getMapPlaylist().values().toArray()[i].toString());
            txt.setMaxWidth(width/2);
            txt.setHeight(150);
            newRow.addView(txt);

            final Spinner spinner = new Spinner(this);
            spinner.setId(i);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView adapter, View view, int i, long id) {
                    selecteditem =  adapter.getItemAtPosition(i).toString();
                    System.out.println(selecteditem);
                    GlobalVars.emotionSequence.add(spinner.getId(),selecteditem);
                    if(GlobalVars.emotionSequence.size() > GlobalVars.playlist.getMapPlaylist().size() ){

                        GlobalVars.emotionSequence.remove((spinner.getId()) + 1);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                    this, android.R.layout.simple_spinner_item, imageEmojiString);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            //caso a activity criar playlist esteja a crashar comentar a proxima linha e vai limpar todas as emoções guarddas
            spinner.setSelection(selectSpinner(spinner));
            newRow.addView(spinner);


            tableMusicEmotion.addView(newRow);

        }


    }
    public int selectSpinner(Spinner spinner){

        String temp = GlobalVars.emotionSequence.get(spinner.getId());
        if(temp.contains("happy"))return 1;
        if(temp.contains("neutral"))return 2;
        if(temp.contains("sad")) return 3;
        else return 0;
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDataEmotions();
        finish();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        saveDataEmotions();

    }

    @Override
    protected void onStop() {
        super.onStop();
        saveDataEmotions();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveDataEmotions();

        webview.clearHistory();
        webview.clearCache(true);
        webview.destroy();
        webview = null;
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
            webview.destroy();
        }
        else {
            Intent intent = new Intent(CriarPlaylistActivity.this, MainActivity.class);
            CriarPlaylistActivity.this.finish();
            startActivity(intent);
            super.onBackPressed();
        }

    }

    public void getYoutubeUrls(){
        Bundle extras = getIntent().getExtras();

        if(extras!=null) {
            String urlReceived = extras.getString(Intent.EXTRA_TEXT);
            String videoTitle = extras.getString(Intent.EXTRA_SUBJECT).split(Pattern.quote("Watch \""))[1];
            String videoTitle2 = videoTitle.split(Pattern.quote("\" on Youtube"))[0];
//            String videoTitle3 = videoTitle.split(videoTitle2)[1];
//            int i = videoTitle.split(" ").length;



            String videoTitleFinal = videoTitle2;

            //adicionar á playlist
            playlist.append(urlReceived);
            playlist.getMapPlaylist().put(urlReceived,videoTitleFinal);
            saveData(urlReceived,videoTitleFinal);
            System.out.println(playlist.getPlaylist());
            System.out.println("Video hash: "+ playlist.getMapPlaylist());
            System.out.println("keysss: "+playlist.getMapPlaylist().values().toArray()[0]);

            SharedPreferences sharedPreferences = getSharedPreferences("videosURL", Context.MODE_PRIVATE);
            System.out.println(sharedPreferences.getAll());
        }

            webview.clearFormData();
    }
    public void saveData(String url, String name){
        SharedPreferences sharedPreferences = getSharedPreferences("videosURL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(url,name);
        editor.apply();
//        editor.clear();
//        editor.commit();

    }
    public void saveDataEmotions(){
        SharedPreferences sharedPrefEmotion = getSharedPreferences("emotions", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefEmotion.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(GlobalVars.emotionSequence);

        editor.putString("key", jsonFavorites);
        editor.apply();
//        editor.clear();
//        editor.commit();

        System.out.println(sharedPrefEmotion.getAll());

    }

    public void changeActivity(Activity activity){//função para mudar de atividade
        Class myActivity = activity.getClass();
        Intent intent = new Intent(CriarPlaylistActivity.this, myActivity);
        startActivity(intent);

    }
}
