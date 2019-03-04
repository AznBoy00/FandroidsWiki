package org.wikipedia.qrcode;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.page.PageBackStackItem;
import org.wikipedia.page.tabs.Tab;

import java.util.EnumMap;
import java.util.Map;

public class QRCodeGenerateActivity extends AppCompatActivity {
    //TODO
    //Create all varibles here. such as imageview which can show the QR code
    private static final String TAG = "QRCodeGenerateActivity";

    protected QRCodeGenerateActivity self;
    protected Snackbar snackbar;
    protected Bitmap qrImage;

    protected TextView qrtitle;
    protected TextView qrdesc;
    protected TextView qrurl;
    protected ImageView imgResult;
    protected ProgressBar loader;

    //using the QRcode funtion
    public static Intent newIntent(@NonNull Context context) {
        return new Intent(context, QRCodeGenerateActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("QRCodeGenerateActivity","QRCodeGenerateActivity_onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_generate);
        self = this;
        imgResult = (ImageView)findViewById(R.id.imgResult);
        qrtitle = (TextView)findViewById(R.id.qrtitle);
        qrdesc = (TextView)findViewById(R.id.qrdesc);
        qrurl = (TextView)findViewById(R.id.qrurl);
        WikipediaApp app = WikipediaApp.getInstance();
        Tab currentTab = app.getTabList().get(app.getTabList().size() - 1); //get latest tab
        PageBackStackItem lastTab = currentTab.getBackStack().get(currentTab.getBackStackPosition());
        qrtitle.setText("Article: " + lastTab.getTitle().getText());
        qrdesc.setText("Description: " + lastTab.getTitle().getDescription());
        qrurl.setText("URL: " + lastTab.getTitle().getCanonicalUri());
        Log.d(TAG, qrtitle.getText() + " " + qrdesc.getText() + " " + qrurl.getText());
        this.generateImage(lastTab.getTitle().getCanonicalUri());
    }

    protected void generateImage(String url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                int size = imgResult.getMeasuredWidth();
                if( size > 1){
                    size = 260;
                    Log.e(TAG, "Size is force set to " + size);
                }

                Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
                hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
                hintMap.put(EncodeHintType.MARGIN, 1);
                QRCodeWriter qrCodeWriter = new QRCodeWriter();
                try {
                    BitMatrix byteMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, size,
                            size, hintMap);
                    int height = byteMatrix.getHeight();
                    int width = byteMatrix.getWidth();
                    self.qrImage = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                    for (int x = 0; x < width; x++){
                        for (int y = 0; y < height; y++){
                            qrImage.setPixel(x, y, byteMatrix.get(x,y) ? Color.BLACK : Color.WHITE);
                        }
                    }

                    self.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            self.showImage(qrImage);
                        }
                    });
                } catch (WriterException e) {
                    e.printStackTrace();
                    alert(e.getMessage());
                }
            }
        }).start();
    }

    private void showImage(Bitmap bitmap) {
        if (bitmap == null) {
            imgResult.setImageResource(android.R.color.transparent);
            qrImage = null;
        } else {
            imgResult.setImageBitmap(bitmap);
        }
    }

    private void alert(String message){
        AlertDialog dlg = new AlertDialog.Builder(this)
                .setTitle("QRCode Generator")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        dlg.show();
    }
}
