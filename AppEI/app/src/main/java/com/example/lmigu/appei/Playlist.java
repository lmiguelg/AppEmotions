package com.example.lmigu.appei;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lmigu on 29/11/2018.
 */

public class Playlist {

    ArrayList <String> playlist = new ArrayList<>();

    HashMap<String, String> mapPlaylist = new HashMap<String, String>();


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

    public HashMap<String, String> getMapPlaylist() {
        return mapPlaylist;
    }
}
