package org.wikipedia.qrcode;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.wikipedia.R;

public class QRCodeGenerateActivity extends AppCompatActivity {

    //TODO
    //Create all varibles here. such as imageview which can show the QR code
    //using the QRcode funtion


    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, QRCodeGenerateActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("QRCodeGenerateActivity","QRCodeGenerateActivity_onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_generate);
    }



}
