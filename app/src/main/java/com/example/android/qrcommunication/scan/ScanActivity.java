package com.example.android.qrcommunication.scan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.qrcommunication.R;

public class ScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
    }

    public void buttonDecodeSingle (View v) {
        Intent intent = new Intent(ScanActivity.this, ScanSingleActivity.class);
        startActivity(intent);
    }

    public void buttonDecodeContinuous (View v) {
        Intent intent2 = new Intent(ScanActivity.this, ScanCameraActivity.class);
        startActivity(intent2);
    }
}
