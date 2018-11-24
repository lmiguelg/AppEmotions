package com.example.lmigu.appei;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.io.Console;

public class CriarPlaylistActivity extends AppCompatActivity {


    private static final int REQ_CODE_PICK_SOUNDFILE = 1;
    ImageButton btnHappy;
    ImageButton btnSad;
    ImageButton btnNeutral;
    ImageButton btnAngry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_playlist);


        //Listener para o btn criar playlist
        btnHappy = findViewById(R.id.btn_happy);
        btnHappy.setOnClickListener(new View.OnClickListener(){

            public void onClick (View v){

                Log.d("value","happy");
            }
        });



        Intent intent;
        intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/mpeg");
        startActivityForResult(Intent.createChooser(intent, getString(R.string.select_audio_file_title)), REQ_CODE_PICK_SOUNDFILE);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE_PICK_SOUNDFILE && resultCode == Activity.RESULT_OK){
            if ((data != null) && (data.getData() != null)){
                Uri audioFileUri = data.getData();
                GlobalVars.uriSound = audioFileUri;
                Log.d("uri da musica: ",audioFileUri.toString());
            }
        }
    }



}
