package com.example.lmigu.appei;

import android.net.Uri;

import java.util.ArrayList;

/**
 * Created by lmigu on 23/11/2018.
 */

public class GlobalVars {

    public static Uri uriSound;

    public static Playlist playlist = new Playlist();



    public static ArrayList<String> urlsYoutube;

    public Uri getUriSound() {
        return uriSound;
    }

    public void setUriSound(Uri uriSound) {
        this.uriSound = uriSound;
    }

    public static ArrayList<String> getUrlsYoutube() {
        return urlsYoutube;
    }

    public static void setUrlsYoutube(ArrayList<String> urlsYoutube) {
        GlobalVars.urlsYoutube = urlsYoutube;
    }
//    public void addUrlYoutube(String url){
//        GlobalVars.urlsYoutube.add(url);
//    }

}
