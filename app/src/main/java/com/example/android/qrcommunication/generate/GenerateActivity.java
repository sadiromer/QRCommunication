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
    }//onCreate

    //Click Text Button to go to GenerateTextActivity
    public void TextDecodeButton (View v) {
        Intent intent = new Intent(GenerateActivity.this, GenerateTextActivity.class);
        startActivity(intent);
    }

    //Click Image Button to go to GenerateImageActivity
    public void ImageDecodeButton (View v) {
        Intent intent2 = new Intent(GenerateActivity.this, GenerateImageActivity.class);
        startActivity(intent2);
    }


    public void ImageDecodeButton2 (View v) {
        Intent intent3 = new Intent(GenerateActivity.this, GenerateSingleImageActivity.class);
        startActivity(intent3);
    }

}//GenerateActivity
