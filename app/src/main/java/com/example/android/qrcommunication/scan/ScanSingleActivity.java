package com.example.android.qrcommunication.scan;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.qrcommunication.R;

import java.util.ArrayList;

public class ScanSingleActivity extends AppCompatActivity {

    //Variable defined
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    ArrayList<String> results;
    public String input = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_single);

        results = new ArrayList<String>();
    }


    //-----------------------------------------------------------

//MY CODE STARTS FROM HERE-----------------------------------
//-----------------------------------------------------------

    /**
     * This method is called when the Scan Button is clicked
     * Uses Intent to find a Zxing Scanning application, and goes to that appliccation
     */
    public void buttonDecode(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(ScanSingleActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }//Scan QR button





    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                String errorCorrectionLevel = intent.getStringExtra("SCAN_RESULT_ERROR_CORRECTION_LEVEL");

                //displayForQR(contents,format,errorCorrectionLevel);
                // Handle successful scan

                // Handle successful scan. In this example add contents to ArrayList
                results.add(contents);

                Intent intent2 = new Intent("com.google.zxing.client.android.SCAN");
                intent2.putExtra("SCAN_FORMATS", "PRODUCT_MODE,CODE_39,CODE_93,CODE_128,DATA_MATRIX,ITF");
                startActivityForResult(intent2, 0); // start the next scan
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel

                int sizeScan = results.size();
                //String input = results.get(0);
                for (int i = 0; i < sizeScan; i++) {
                    input += results.get(i);
                }

                int textLength = input.length();

                TextView displayView = (TextView) findViewById(R.id.decodeData);
                displayView.setMovementMethod(new ScrollingMovementMethod());
                displayView.setText(String.valueOf(input));
                displayView.setTextIsSelectable(true);

                TextView displayView2 = (TextView) findViewById(R.id.numberOfQrCodesScanned);
                displayView2.setText(String.valueOf(sizeScan));

                TextView displayView3 = (TextView) findViewById(R.id.lengthOfText);
                displayView3.setText(String.valueOf(textLength));

            }
        }
    } //public void onActivityResults

    public void buttonCreate(View view){
        Bitmap Image = decodeBase64(input);

        //Set it in ImageView
        ImageView QRView = (ImageView) findViewById(R.id.imageDecoded);
        QRView.setImageBitmap(Image);
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }


    public void displayForQR(String qrData, String format, String errorCorrectionLevel) {
        TextView displayView = (TextView) findViewById(R.id.decodeData);
        displayView.setMovementMethod(new ScrollingMovementMethod());


        String qrSummary = "Format: " + format;
        qrSummary += "\nError Correction Level: " + errorCorrectionLevel;
        qrSummary += "\n\nReceive Data: \n";
        qrSummary += qrData;
        displayView.setText(String.valueOf(qrSummary));
    }//public void displayForQR

    //The following is to decode. CAn use it in the decoding part
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


}
