package com.example.android.qrcommunication.generate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.qrcommunication.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Hashtable;

public class GenerateSingleImageActivity extends AppCompatActivity {

    //Define Variables
    private static final int REQUEST_ID = 1;
    private static final int HALF = 2;
    public int number = 0;
    public EditText picture;
    public ArrayList<Bitmap> bmp_images = new ArrayList<Bitmap>();

    public String input = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_single_image);

        picture = (EditText) findViewById(R.id.pictureNumber);
    }//onCreate


    //Using this to search only for Image Types
    public void browseButton(View v) {
        Intent intentImage = new Intent();
        intentImage.setAction(Intent.ACTION_GET_CONTENT);
        intentImage.addCategory(Intent.CATEGORY_OPENABLE);
        intentImage.setType("image/*");
        startActivityForResult(intentImage, REQUEST_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        InputStream imageStream = null;
        if (requestCode == REQUEST_ID && resultCode == Activity.RESULT_OK) {
            try {
                Uri selectedImage = data.getData(); //path of the image file chosen
                imageStream = getContentResolver().openInputStream(selectedImage); //streams in the image
                Bitmap bmpImage = BitmapFactory.decodeStream(imageStream); //converts the image to bitmap


                //adding me code to convert to base64
                String Base64 = encodeTobase64(bmpImage);

                //Splitting the base64 strings into parts
                int splitStringLength = 1000; //Number of parts base64 is to be split
                ArrayList<String> Base64Parts = splitEqually(Base64, splitStringLength);

                //Set it in textview
                TextView displayView = (TextView) findViewById(R.id.base64text);
                displayView.setMovementMethod(new ScrollingMovementMethod());
                displayView.setText(String.valueOf(Base64));
                displayView.setTextIsSelectable(true);

                //Getting the length of the string and displaying it
                int length = Base64.length();


                //Number of parts its been split into
                int numberOfPartsSplit;
                if (length % splitStringLength == 0) {
                    numberOfPartsSplit = (length / splitStringLength);
                } else {
                    numberOfPartsSplit = (length / splitStringLength) + 1;
                }


                //int length = Base64Parts[1].length();
                TextView displayView2 = (TextView) findViewById(R.id.base64details);
                displayView2.setText(String.valueOf(length));

                TextView displayView3 = (TextView) findViewById(R.id.base64details2);
                displayView3.setText(" 0 - " + String.valueOf(numberOfPartsSplit - 1));


                //-------------------Generating a QR Code-------------------------------------------

                //Declaring QR code generator
                QRCodeWriter writer = new QRCodeWriter();


                //For loop for generating multiple QR codes
                for (int i = 0; i <= numberOfPartsSplit; i++) {
                    try {
                        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap =
                                new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
                        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
                        BitMatrix bitMatrix = writer.encode(Base64Parts.get(i),
                                BarcodeFormat.QR_CODE, 512, 512, hintMap);
                        int width = bitMatrix.getWidth();
                        int height = bitMatrix.getHeight();
                        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

                        //Creating the bitmap matrix for QR code Image
                        for (int x = 0; x < width; x++) {
                            for (int y = 0; y < height; y++) {
                                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                            }
                        }//for loop to generate QR Code Bitmap

                        //the code added for arraylist of images
                        bmp_images.add(i, bmp);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }//forloop
                //----------------------------------------------------------------------------------


            } catch (Exception e) {
                e.printStackTrace();
            }

            if (imageStream != null) {
                try {
                    imageStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }//if as requested from button
    }//onActivityResult

    //-------------------Generating Scroll Image----------------------------------------

    public void okButton (View view){
        number = Integer.parseInt(picture.getText().toString());
        Bitmap image = bmp_images.get(number);

        ImageView imageAnim = (ImageView) findViewById(R.id.image_holder);
        imageAnim.setImageBitmap(image);
    }
    //----------------------------------------------------------------------------------


    //___________________________________FUNCTIONS USED_____________________________________________

    //The following is to encode the the image to Base64
    public static String encodeTobase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

        Log.e("LOOK", imageEncoded);
        return imageEncoded;
    }


    //Function to Split String
    public static ArrayList<String> splitEqually(String text, int size) {
        // Give the list the right capacity to start with. You could use an array
        // instead if you wanted.
        ArrayList<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

        for (int start = 0; start < text.length(); start += size) {
            ret.add(text.substring(start, Math.min(text.length(), start + size)));
        }
        return ret;
    }


    //___________________________________FUNCTIONS USED_____________________________________________


}//GenerateSingleImageActivity
