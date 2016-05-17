package com.example.android.qrcommunication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.android.qrcommunication.generate.GenerateActivity;
import com.example.android.qrcommunication.scan.ScanActivity;

/**
 * @author Sadir Omer
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.main);

        ImageView image = (ImageView) findViewById(R.id.image_holder);
        image.setImageBitmap(icon);
    }//onCreate

   //Click Button to go to Generate Activity
    public void generateButton (View v) {
        Intent intent = new Intent(MainActivity.this, GenerateActivity.class);
        startActivity(intent);
    }

    //Click Button to go to ScanActivity
    public void scanButton (View v) {
        Intent intent2 = new Intent(MainActivity.this, ScanActivity.class);
        startActivity(intent2);
    }



}//MainActivity
