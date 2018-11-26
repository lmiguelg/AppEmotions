package com.example.lmigu.appei;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    Button btnCriarPlaylist;
    Button btnOuvirMusica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyPermissions();

        //executa uma ação depois do unlock do telemovel
        registerReceiver(new PhoneUnlockedReceiver(), new IntentFilter("android.intent.action.USER_PRESENT"));

        //abre a preview da camera
        dispatchTakePictureIntent();



        //Listener para o btn criar playlist
        btnCriarPlaylist = findViewById(R.id.btn_criarPlaylist);
        btnCriarPlaylist.setOnClickListener(new View.OnClickListener(){

            public void onClick (View v){

                changeActivity(new CriarPlaylistActivity () );
            }
        });

        btnOuvirMusica = findViewById(R.id.btn_ouvirMusica);
        btnOuvirMusica.setOnClickListener(new View.OnClickListener(){

            public void onClick (View v){

                changeActivity(new MusicPlayerActivity () );

            }
        });




    }



    public void changeActivity(Activity activity){//função para mudar de atividade
        Class myActivity = activity.getClass();
        Intent intent = new Intent(MainActivity.this, myActivity);
        startActivity(intent);

    }

    private void verifyPermissions(){
        Log.d("permissions: ", "ask for permissiosn");

        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_DOCUMENTS};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[1]) == PackageManager.PERMISSION_GRANTED
                &&ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[2]) == PackageManager.PERMISSION_GRANTED){
            Log.d("ok","ok permissions");

        }
        else{
            Log.d("ok","not ok permissions");
            ActivityCompat.requestPermissions(this,permissions,REQUEST_CODE);
        }
    }










    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
