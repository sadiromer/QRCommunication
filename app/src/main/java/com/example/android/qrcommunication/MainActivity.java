package com.example.android.qrcommunication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.qrcommunication.generate.GenerateActivity;
import com.example.android.qrcommunication.scan.ScanActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Method to create a 2nd activity to Generate QR code
     */
    public void generateButton (View v) {
        Intent intent = new Intent(MainActivity.this, GenerateActivity.class);
        startActivity(intent);
    }

    public void scanButton (View v) {
        Intent intent2 = new Intent(MainActivity.this, ScanActivity.class);
        startActivity(intent2);
    }

}
