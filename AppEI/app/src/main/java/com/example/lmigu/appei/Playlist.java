package com.example.lmigu.appei;

import java.util.ArrayList;

/**
 * Created by lmigu on 29/11/2018.
 */

public class Playlist {

    ArrayList <String> playlist = new ArrayList<>();

    public ArrayList<String> getPlaylist() {
        return playlist;
    }
    public void append(String url){
        if(playlist.contains(url))return;
        playlist.add(url);
    }
    public void remove(String url){
        if(!playlist.contains(url))return;
        playlist.remove(url);
    }
}
