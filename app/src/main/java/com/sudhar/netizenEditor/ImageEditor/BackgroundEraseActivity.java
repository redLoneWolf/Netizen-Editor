package com.sudhar.netizenEditor.ImageEditor;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.sudhar.netizenEditor.R;

import java.io.File;

public class BackgroundEraseActivity extends AppCompatActivity {
    String path;
    Bitmap bmp1,bmp;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_erase);


        //Create a object of custom view
        BackgroundEraseView myCustomView = new BackgroundEraseView(BackgroundEraseActivity.this);

        Bundle extras = getIntent().getExtras();
        String b = extras.getString("picture");
        path = b;
        File imgFile = new File(b);
        if (imgFile.exists()) {
            uri = Uri.fromFile(imgFile);
            bmp = BitmapFactory.decodeFile(imgFile.getAbsolutePath());



            myCustomView.setRawBitmap(bmp);



            Bitmap.Config bitmapConfig = bmp.getConfig();
            if (bitmapConfig == null) {
                bitmapConfig = Bitmap.Config.ARGB_8888;
            }
            bmp1 = bmp.copy(bitmapConfig, true);
        }

        //Get root layout of the activity
        RelativeLayout rootLayout = findViewById(R.id.erase);

        //Add custom view into root layout
        rootLayout.addView(myCustomView);

    }
}