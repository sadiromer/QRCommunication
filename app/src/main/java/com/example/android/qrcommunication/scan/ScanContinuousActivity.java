package com.example.android.qrcommunication.scan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.qrcommunication.R;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class ScanContinuousActivity extends AppCompatActivity {

    public ArrayList<String> StringResults = new ArrayList<String>();
    public int sizeScan = 0;
    public ArrayList<String> FinalString = new ArrayList<String>();
    public int Phase = 1;
    public String input = "";
    public Bitmap Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_continuous);

        StringResults = (ArrayList<String>) getIntent().getSerializableExtra("FILES_TO_SEND");


        //------------------------------------------------------------------------------------------
        TextView displayView = (TextView) findViewById(R.id.base64details2);
        displayView.setMovementMethod(new ScrollingMovementMethod());
        displayView.setMaxLines(3);
        displayView.setText(String.valueOf(StringResults));

        sizeScan = StringResults.size();
        TextView displayView2 = (TextView) findViewById(R.id.base64details);
        displayView2.setMovementMethod(new ScrollingMovementMethod());
        displayView2.setText(String.valueOf(sizeScan));

        switch (Phase) {
            case 1:
                for (int i = 0; i < sizeScan; i++) {
                    if (!(StringResults.get(i).equals("START"))) {
                        if (!(StringResults.get(i).equals(StringResults.get(i - 1)))) {
                            FinalString.add(StringResults.get(i));


                            if (StringResults.get(i).equals("START")) {

                                Phase = 2;


                            }
                        }

                    }

                }
            case 2:
                TextView displayView3 = (TextView) findViewById(R.id.base64text);
                displayView3.setMovementMethod(new ScrollingMovementMethod());
                displayView3.setMaxLines(3);
                displayView3.setText(String.valueOf(FinalString));


                int size = FinalString.size();

                for (int j = 0; j < size; j++) {
                    input += FinalString.get(j);
                }

                TextView displayView4 = (TextView) findViewById(R.id.sizeString);
                displayView4.setMovementMethod(new ScrollingMovementMethod());
                displayView4.setMaxLines(3);
                displayView4.setText(String.valueOf(size));

            default:
                //throw new IllegalArgumentException("Found empty contents");
                break;
        }

    }

    public void buttonDecodeGenerate (View v) {

        int length = input.length();
        TextView displayView5 = (TextView) findViewById(R.id.lengthString);
        displayView5.setText(String.valueOf(length));

        Image = decodeBase64(input);
        ImageView QRView = (ImageView) findViewById(R.id.image_holder);
        QRView.setImageBitmap(Image);
    }


    //The following is to decode. CAn use it in the decoding part
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    private byte[] getRawBytes(Result result) {
        Log.i("getRawBytes()", " ");//TODO delete after debugging

        Map<ResultMetadataType, Object> hashtable = result.getResultMetadata();
        Vector<byte[]> segments = (Vector<byte[]>) hashtable
                .get(ResultMetadataType.BYTE_SEGMENTS);
        int byteNum = 0;
        for (byte[] array : segments)
            byteNum += array.length;
        byte[] rawBytes = new byte[byteNum];
        int index = 0;
        for (byte[] array : segments)
            for (int i = 0; i < array.length; i++, index++){
                rawBytes[index] = array[i];
//				System.out.print(rawBytes[index] + " ");//TODO delete after debugging
            }

        return rawBytes;
    }

}
