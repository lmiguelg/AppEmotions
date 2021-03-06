package com.example.lmigu.appei;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;
    Button btnCriarPlaylist;

    Button btnShowEmotion;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{


            GlobalVars.urlsYoutube = new ArrayList<>();
            GlobalVars.emotionSequence = new ArrayList<>();
            inicializaDadosStorage();
            verifyPermissions();




            //executa uma ação depois do unlock do telemovel
            registerReceiver(new PhoneUnlockedReceiver(), new IntentFilter("android.intent.action.USER_PRESENT"));

            //abre a preview da camera
            dispatchTakePictureIntent();

            //Listener para o btn criar playlist
            btnCriarPlaylist = findViewById(R.id.btn_criarPlaylist);
            btnCriarPlaylist.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    changeActivity(new CriarPlaylistActivity () );
                }
            });


            btnShowEmotion = findViewById(R.id.btn_show_emotion);
            btnShowEmotion.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    changeActivity(new ShowEmotionActivity());
                }
            });

            System.out.println(GlobalVars.mapMusicEmotion.values());
        }catch(Exception e){

        }

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

        try{
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);

            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

                if(Uri.fromFile(getOutputPhotoFile()) != null){




                Uri photoUri = Uri.fromFile(getOutputPhotoFile());
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                //takePictureIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 1);
                //takePictureIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
                startActivityForResult(takePictureIntent, 1);
                }
            }
        }catch(Exception e){}
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
        try{
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        }catch(Exception e){

        }


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    public void inicializaDadosStorage(){

        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("videosURL", Context.MODE_PRIVATE);
        Map<String, ?> allEntries = pSharedPref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            GlobalVars.playlist.getMapPlaylist().put(entry.getKey(),entry.getValue().toString());
        }

        SharedPreferences sharedPrefEmotion = getApplicationContext().getSharedPreferences("emotions", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPrefEmotion.getString("key", "");
        ArrayList temp = new ArrayList();
        temp = gson.fromJson(json,ArrayList.class);
        GlobalVars.emotionSequence = temp;
        System.out.println(GlobalVars.emotionSequence);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try{

        }catch (Exception e){}
    }
}
