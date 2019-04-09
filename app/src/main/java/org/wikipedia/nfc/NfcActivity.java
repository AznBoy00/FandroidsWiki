package org.wikipedia.nfc;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import org.wikipedia.R;
import org.wikipedia.WikipediaApp;
import org.wikipedia.page.PageBackStackItem;
import org.wikipedia.page.tabs.Tab;

import java.util.List;

public class NfcActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {

    NfcAdapter nfcAdapter;
    String useOfNFC;
    WebView webView;
    FrameLayout nfcFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        // Declarations
        webView = (WebView) findViewById(R.id.webview);
        nfcFrameLayout = findViewById(R.id.nfc_frame_layout);
        Toolbar toolbar = findViewById(R.id.page_toolbar);

        // Set the action bar and when clicked (Wikipedia back button), will finish the activity
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Intent intent = getIntent();
        useOfNFC = intent.getStringExtra("for");

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "Your device does not support NFC", Toast.LENGTH_LONG).show();
            // Will return to previous page if NFC is not supported
            finish();
            return;
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC.", Toast.LENGTH_LONG).show();
        }

        nfcAdapter.setNdefPushMessageCallback(this, this);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String data = "";
        if(useOfNFC.equals("article")) {
            WikipediaApp app = WikipediaApp.getInstance();
            Tab currentTab = app.getTabList().get(app.getTabList().size() - 1);
            PageBackStackItem lastTab = currentTab.getBackStack().get(currentTab.getBackStackPosition());
            data = useOfNFC + "," + lastTab.getTitle().getCanonicalUri();
        } else if(useOfNFC.equals("note")) {
            //DO SOMETHING FOR NOTES
        }
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", data.getBytes());
        NdefMessage message = new NdefMessage(ndefRecord);
        return message;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            showMessage(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    public void showMessage(Intent intent) {
        Parcelable[] message = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage nfcMessage = (NdefMessage) message[0];
        String rawData = new String(nfcMessage.getRecords()[0].getPayload());
        String[] data = rawData.split(",");
        if(data[0].equals("article")) {
            String url = data[1];
            nfcFrameLayout.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(url);
        } else if(data[0].equals("note")) {
            // DO SOMETHING FOR NOTES
        }
    }

    /**
     * Will close the activity when pressed on back button (Android)
     */
    @Override
    public void onBackPressed() {
        finish();
    }

}
