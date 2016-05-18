package com.example.android.qrcommunication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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
