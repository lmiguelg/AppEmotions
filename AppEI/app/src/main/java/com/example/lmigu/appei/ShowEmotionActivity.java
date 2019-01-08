package com.example.lmigu.appei;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URI;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;


public class ShowEmotionActivity extends AppCompatActivity {

    private static final String uriBase =
            "https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect";
    String finalEmotion = "";
    Button btnPlayMusic;
    private Random randomGenerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_emotion);
        randomGenerator = new Random();
        Emotion emotion = new Emotion();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(findPhoto() == 0){
            /*
            deu merda e apagar a ultima foto" ou ligar a net*/
        }else{
            //colocar o smile correspondente
            colocaEmotion();

        }
        btnPlayMusic = findViewById(R.id.btnPlayMusic);
        btnPlayMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("asddddddddddddddddddddddddddddddddddddddddddddd");
                System.out.println(GlobalVars.playlist.getMapPlaylist());
                System.out.println(GlobalVars.emotionSequence);
                ArrayList<Integer> indexEmotion = new ArrayList<>();
                for(int i = 0; i < GlobalVars.emotionSequence.size(); i++){
                    if(GlobalVars.emotionSequence.get(i).contains(finalEmotion)){
                        indexEmotion.add(i);
                    }
                }
                int rand = randomGenerator.nextInt(indexEmotion.size());

                String valueMusica = GlobalVars.playlist.mapPlaylist.values().toArray()[indexEmotion.get(rand)].toString();
                String urlMusica = GlobalVars.playlist.mapPlaylist.keySet().toArray()[indexEmotion.get(rand)].toString();
                System.out.println(indexEmotion);
                System.out.println(rand);
                System.out.println(valueMusica);
                System.out.println(urlMusica);

                WebView webview = findViewById(R.id.webviewPlayMusic);
                webview.loadUrl(urlMusica);

            }
        });



    }
    public void colocaEmotion(){
        ImageView myImage = (ImageView) findViewById(R.id.imageView2);
        if (finalEmotion == "happy"){
            myImage.setImageDrawable(getDrawable(R.drawable.happy_emoji));
        }
        if (finalEmotion == "sad"){
            myImage.setImageDrawable(getDrawable(R.drawable.sad_emoji));
        }
        if (finalEmotion == "neutral"){
            myImage.setImageDrawable(getDrawable(R.drawable.neutral_emoji));
        }
    }

    public int findPhoto(){

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



        //myImage.setImageBitmap(myBitmap);
        System.out.println("last file" + lastFile);






        //º+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        HttpClient httpclient = new DefaultHttpClient();

        try
        {

            HttpPost request = new HttpPost("https://westeurope.api.cognitive.microsoft.com/face/v1.0/detect?returnFaceId=true&returnFaceLandmarks=false&returnFaceAttributes=emotion");

            // Request headers.
            request.setHeader("Content-Type", "application/octet-stream");
            request.setHeader("Ocp-Apim-Subscription-Key", "9f6245dfcfa54b9ca2620ccc58458afe");

            // Request body.
            //StringEntity reqEntity = new StringEntity();

            //File file = new File(choice.getPath());
            FileEntity reqEntity = new FileEntity(choice,"application/octet-stream");

            request.setEntity(reqEntity);



            // Execute the REST API call and get the response entity.
            HttpResponse response = httpclient.execute(request);
            HttpEntity entity = response.getEntity();


            if (entity != null)
            {

                // Format and display the JSON response.
                System.out.println("REST Response:\n");


                String jsonString = EntityUtils.toString(entity);
                System.out.println(jsonString);

                String conteudo = jsonString.split("\"faceAttributes\":")[1];
                String conteudo2 = conteudo.split(Pattern.quote("{\"emotion\":"))[1];

                System.out.println(conteudo2);
                System.out.println(conteudo2.split(Pattern.quote("}}]"))[0]);
                String conteudoFinal = conteudo2.split(Pattern.quote("}}]"))[0];
                JSONObject jsonObj = new JSONObject(conteudoFinal);

                String happiness = jsonObj.getString("happiness");
                String sadness = jsonObj.getString("sadness");
                String neutral = jsonObj.getString("neutral");


                System.out.println("happy: " +happiness);
                System.out.println("sad: " + sadness);
                System.out.println("neutral: " +neutral);

                Double fHappyness = Double.parseDouble(happiness);
                Double fSadness = Double.parseDouble(sadness);
                Double fNeutral = Double.parseDouble(neutral);

                System.out.println("happy: " +fHappyness);
                System.out.println("sad: " + fSadness);
                System.out.println("neutral: " +fNeutral);


                System.out.println( getEmotion(fHappyness, fSadness, fNeutral));
                //System.out.println(getEmotion(0.03999,0.6687,0.528));

                return 1;
            }

        }catch(Exception e){
            Log.e("MYAPP", "exception", e);
            return 0;
        }

        /*
        * enviar a ultima foto para a api
        * retornar respost
        * se detetear a emoção fica essa
        * caso contrário apagar a imagem do dir e usar a ultima
        * */



        return 0;

    }



    public String getEmotion(Double happy,Double sad, Double neutral){


        finalEmotion = "neutral";

        if(sad > happy && sad > neutral){
            finalEmotion = "sad";
        }
        if(happy > neutral && happy > sad ){
            finalEmotion = "happy";
        }




        return finalEmotion;
    }

}
