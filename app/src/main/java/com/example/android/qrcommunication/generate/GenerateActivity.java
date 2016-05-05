package com.example.android.qrcommunication.generate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.android.qrcommunication.R;

public class GenerateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);
    }

    public void TextDecodeButton (View v) {
        Intent intent = new Intent(GenerateActivity.this, GenerateTextActivity.class);
        startActivity(intent);
    }

    public void ImageDecodeButton (View v) {
        Intent intent2 = new Intent(GenerateActivity.this, GenerateImageActivity.class);
        startActivity(intent2);
    }

}
