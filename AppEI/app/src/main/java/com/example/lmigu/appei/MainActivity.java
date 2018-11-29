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
import android.os.StrictMode;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    Button btnCriarPlaylist;
    Button btnOuvirMusica;
    Button btnShowEmotion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyPermissions();

        //executa uma ação depois do unlock do telemovel
        registerReceiver(new PhoneUnlockedReceiver(), new IntentFilter("android.intent.action.USER_PRESENT"));

        //abre a preview da camera
        //dispatchTakePictureIntent();

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
        btnShowEmotion = findViewById(R.id.btn_show_emotion);
        btnShowEmotion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                changeActivity(new ShowEmotionActivity());
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
                &&ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[2]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[3]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),permissions[4]) == PackageManager.PERMISSION_GRANTED){
            Log.d("ok","ok permissions");

        }
        else{
            Log.d("ok","not ok permissions");
            ActivityCompat.requestPermissions(this,permissions,REQUEST_CODE);
        }
    }


    private void dispatchTakePictureIntent() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            Uri photoUri = Uri.fromFile(getOutputPhotoFile());
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
            takePictureIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
            takePictureIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
            startActivityForResult(takePictureIntent, 0);
        }
    }


    //gurda a foto num diretorio definido
    private File getOutputPhotoFile() {
        File directory = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getPackageName());
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                Log.d("TAG", "Failed to create storage directory.");
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US).format(new Date());
        Log.d("dir photo",directory.getPath());
        return new File(directory.getPath() + File.separator + "IMG_"
                + timeStamp + ".jpg");
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
