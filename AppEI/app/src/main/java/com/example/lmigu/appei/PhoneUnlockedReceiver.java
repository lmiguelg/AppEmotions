package com.example.lmigu.appei;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by lmigu on 25/11/2018.
 */

public class PhoneUnlockedReceiver extends BroadcastReceiver {
    int i = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            Log.d("TAG", "Phone unlocked");
            //quando dá unlock é inicializado a main activity com a camera;
            Intent takePictureIntent = new Intent();
            takePictureIntent.setClassName("com.example.lmigu.appei","com.example.lmigu.appei.MainActivity");
            takePictureIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(takePictureIntent);








        }else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
            Log.d("TAG", "Phone locked");
        }
    }






}
