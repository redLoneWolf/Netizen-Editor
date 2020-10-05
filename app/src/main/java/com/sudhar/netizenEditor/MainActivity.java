package com.sudhar.netizenEditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;



import android.Manifest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.sudhar.netizenEditor.ImageEditor.ImageEditor;
import com.sudhar.netizenEditor.ImageEditor.ImagePath;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



public class MainActivity extends AppCompatActivity  {

    public static final String TAG = "MainActivity";

    private static final int PICK_REQUEST = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences("Netizen",MODE_PRIVATE);


        setContentView(R.layout.activity_main);

        Button gallery = findViewById(R.id.GalleryBtn);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });






//For First Start-----------------------------------

        boolean firstStart = preferences.getBoolean("firstStart",true);
        if(firstStart){
            copyAsset("stickers");
            Toast.makeText(MainActivity.this, "Copying Assets", Toast.LENGTH_SHORT).show();
            File dir = new File( Environment.getExternalStorageDirectory(), "font");
            dir.mkdirs();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstStart",false);
            editor.apply();
        }
//--------------------------------------------------




        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);



    }

    private void getImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PICK_REQUEST:
                    Uri uri = data.getData();

                    String realPath = ImagePath.getPath(MainActivity.this, data.getData());
                    Log.i(TAG, "onActivityResult: file path : " + realPath);
                    Intent intent=new Intent(this, ImageEditor.class);
                    intent.putExtra("picture", realPath);
                    startActivity(intent);
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


private void copyAsset(String path) {
    AssetManager manager = getAssets();

    // If we have a directory, we make it and recurse. If a file, we copy its
    // contents.
    try {
        String[] contents = manager.list(path);

        // The documentation suggests that list throws an IOException, but doesn't
        // say under what conditions. It'd be nice if it did so when the path was
        // to a file. That doesn't appear to be the case. If the returned array is
        // null or has 0 length, we assume the path is to a file. This means empty
        // directories will get turned into files.
        if (contents == null || contents.length == 0)
            throw new IOException();

        // Make the directory.
        File dir = new File( Environment.getExternalStorageDirectory(), path);
        dir.mkdirs();

        // Recurse on the contents.
        for (String entry : contents) {
            copyAsset(path + "/" + entry);
        }
    } catch (IOException e) {
        copyFileAsset(path);
    }
}

    /**
     * Copy the asset file specified by path to app's data directory. Assumes
     * parent directories have already been created.
     *
     * @param path
     * Path to asset, relative to app's assets directory.
     */
    private void copyFileAsset(String path) {
        File file = new File(Environment.getExternalStorageDirectory(), path);
        try {
            InputStream in = getAssets().open(path);
            OutputStream out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read = in.read(buffer);
            while (read != -1) {
                out.write(buffer, 0, read);
                read = in.read(buffer);
            }
            out.close();
            in.close();
        } catch (IOException e) {

        }
    }






}




