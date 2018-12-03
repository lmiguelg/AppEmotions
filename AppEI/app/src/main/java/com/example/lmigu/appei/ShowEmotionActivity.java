package com.example.lmigu.appei;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.widget.ImageView;

import java.io.File;
import java.io.FileFilter;

public class ShowEmotionActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_emotion);

        findPhoto();


    }

    public void findPhoto(){

        File fl = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getPackageName());
        File[] files = fl.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        });
        long lastMod = Long.MIN_VALUE;
        File choice = null;
        for (File file : files) {
            if (file.lastModified() > lastMod) {
                choice = file;
                lastMod = file.lastModified();
            }
        }
        String lastFile = choice.getPath();
        Bitmap myBitmap = BitmapFactory.decodeFile(choice.getAbsolutePath());

        ImageView myImage = (ImageView) findViewById(R.id.imageView2);

        myImage.setImageBitmap(myBitmap);
        System.out.println("last file" + lastFile);

        /*
        * enviar a ultima foto para a api
        * retornar respost
        * se detetear a emoção fica essa
        * caso contrário apagar a imagem do dir e usar a ultima
        * */
    }
}
