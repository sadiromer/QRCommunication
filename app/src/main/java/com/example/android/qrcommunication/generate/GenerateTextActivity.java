package com.example.android.qrcommunication.generate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.android.qrcommunication.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GenerateTextActivity extends AppCompatActivity {

    //Defined Variables
    public String insertTxtCopy;
    public EditText qrTextField;
    public ImageLoader imgLoader;
    public ImageView qrImg;
    public CharSequence clipTxt;
    public Spinner spinner;
    public String selectedItemError;

    String BASE_QR_URL = "http://chart.apis.google.com/chart?cht=qr&chs=400x400&chld=M&choe=UTF-8&chl=";
    String fullUrl = BASE_QR_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_text);

        //----------------------------------------------------------------
        //My code inside the onCreate. Defining require fields------------
        //----------------------------------------------------------------

        //Defining the external library thats used to generate the Image for the generated QR
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        imgLoader = ImageLoader.getInstance();
        imgLoader.init(config);

        //Defining the editText Field
        //Setting the field to be scrollable with the 1 line view
        qrTextField = (EditText) findViewById(R.id.textQR);
        qrTextField.setMaxLines(1);
        qrTextField.setVerticalScrollBarEnabled(true);
        qrTextField.setMovementMethod(new ScrollingMovementMethod());

        //Defining the imageView thats used to generate the QR image
        qrImg = (ImageView) findViewById(R.id.qrImg);

        //Defining the Spinner used to choose the type of Error Correction level used
        //The variables in the spinner are defined in strings.xml
        // 'errorCorrectionMethod' is the variable used in strings.xml
        // 'simple_spinner_method' is the type of spinner used
        //An adapter is used to set the variables to the spinner

        //Start of Defining Spinner----------------------------------------
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.errorCorrectionMethod, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemError = spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //End of Spinner for ECM------------------------------------------

    }//onCreate

    //----------------------------------------------------------------
    //MY CODE STARTS FROM HERE----------------------------------------
    //----------------------------------------------------------------

    /**
     * Button generates the QR code for the given specific data and error correction level
     * A URL provided by google is used to generate the QR code
     * 'insertTxtCopy' used to get data from the textField and copied into 'clipTxt'
     * The image is loaded using the external library
     * If there is no data, a Toast is displayed
     */
    public void buttonQR(View v) {
        insertTxtCopy = qrTextField.getText().toString();
        clipTxt = insertTxtCopy;

        //If the clipboard has text, and it is more than 0 characters.
        if ((null != clipTxt) && (clipTxt.length() > 0)) {
            try {
                String copiedStr = clipTxt.toString();
                fullUrl = "http://chart.apis.google.com/chart?cht=qr&chs=400x400&chld=" + selectedItemError + "&choe=UTF-8&chl=" + URLEncoder.encode(copiedStr, "UTF-8");

                imgLoader.displayImage(fullUrl, qrImg);


            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else { //If no text display a dialog.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Error")
                    .setCancelable(true)
                    .setMessage("Please Insert Text")
                    .setNeutralButton("Okay", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();

                        }

                    });

            AlertDialog diag = builder.create();
            diag.show();
        }
    }//Public void button QR
}
