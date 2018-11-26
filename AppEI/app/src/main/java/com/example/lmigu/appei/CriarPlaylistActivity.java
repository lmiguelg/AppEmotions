package com.example.lmigu.appei;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.io.Console;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class CriarPlaylistActivity extends AppCompatActivity {


    ArrayList<String> list = new ArrayList<>();

    private static final int REQ_CODE_PICK_SOUNDFILE = 1;
    ImageButton btnHappy;
    ImageButton btnSad;
    ImageButton btnNeutral;
    ImageButton btnAngry;
    Uri audioFileUri;
    Spinner spinnerMusicas, spinnerMusicas2,spinnerMusicas3,spinnerMusicas4,spinnerMusicas5,spinnerMusicas6,spinnerMusicas7,spinnerMusicas8,spinnerMusicas9,spinnerMusicas10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_criar_playlist);
        spinnerMusicas = findViewById(R.id.spinner1);
        spinnerMusicas2 = findViewById(R.id.spinner2);
        spinnerMusicas3 = findViewById(R.id.spinner3);
        spinnerMusicas4 = findViewById(R.id.spinner4);
        spinnerMusicas5 = findViewById(R.id.spinner5);
        spinnerMusicas6 = findViewById(R.id.spinner6);
        spinnerMusicas7 = findViewById(R.id.spinner7);
        spinnerMusicas8 = findViewById(R.id.spinner8);
        spinnerMusicas9 = findViewById(R.id.spinner9);
        spinnerMusicas10 = findViewById(R.id.spinner10);

        criarPlayListRaw();
        convertToSpinner(list,spinnerMusicas);
        convertToSpinner(list,spinnerMusicas2);
        convertToSpinner(list,spinnerMusicas3);
        convertToSpinner(list,spinnerMusicas4);
        convertToSpinner(list,spinnerMusicas5);
        convertToSpinner(list,spinnerMusicas6);
        convertToSpinner(list,spinnerMusicas7);
        convertToSpinner(list,spinnerMusicas8);
        convertToSpinner(list,spinnerMusicas9);
        convertToSpinner(list,spinnerMusicas10);


        //Listener para o btn criar playlist
//        btnHappy = findViewById(R.id.btn_happy);
//        btnHappy.setOnClickListener(new View.OnClickListener(){
//
//            public void onClick (View v){
//                Log.d("value","happy");
//            }
//        });
    }

    public void convertToSpinner(ArrayList<String> list,Spinner spinner){
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void criarPlayListRaw(){

        Field[] fields = R.raw.class.getFields();
        for(Field f : fields)
            try {
                list.add(f.getName());
                Log.d("music", f.getName());

            } catch (IllegalArgumentException e) {
            }
    }
}
