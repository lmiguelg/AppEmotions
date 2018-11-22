package com.example.lmigu.appei;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    Button btnCriarPlaylist;
    Button btnOuvirMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Listener para o btn criar playlist
        btnCriarPlaylist = findViewById(R.id.btn_criarPlaylist);
        btnCriarPlaylist.setOnClickListener(new View.OnClickListener(){

            public void onClick (View v){

                changeActivity(new CriarPlaylistActivity () );
            }
        });

    }

    public void changeActivity(Activity activity){//função para mudar de atividade
        Class myActivity = activity.getClass();
        Intent intent = new Intent(MainActivity.this, myActivity);
        startActivity(intent);

    }
}
