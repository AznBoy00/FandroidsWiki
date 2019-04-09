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

public class NfcActivity extends Activity implements NfcAdapter.CreateNdefMessageCallback {

    NfcAdapter nfcAdapter;
    String useOfNFC;
    WebView webView;
    FrameLayout nfcFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        Button buttonSend = findViewById(R.id.button_share);
        Button buttonReceive = findViewById(R.id.button_receive);
        webView = (WebView) findViewById(R.id.webview);
        nfcFrameLayout = findViewById(R.id.nfc_frame_layout);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "Your device does not support NFC", Toast.LENGTH_LONG).show();
            // Will return to previous page if NFC is not supported
            finish();
            return;
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC.", Toast.LENGTH_LONG).show();
        }

        Intent intent = getIntent();
        useOfNFC = intent.getStringExtra("for");
    }

    /**
     * This method is called when clicked on the Share Article button
     * When the user clicks on the Share Article button, it will send the link via NFC
     */
    public void sendArticle(View view) {
//        WikipediaApp app = WikipediaApp.getInstance();
//        Tab currentTab = app.getTabList().get(app.getTabList().size() - 1);
//        PageBackStackItem lastTab = currentTab.getBackStack().get(currentTab.getBackStackPosition());
//        String url = lastTab.getTitle().getCanonicalUri();
//        Intent i = new Intent(Intent.ACTION_VIEW);
//        i.setData(Uri.parse(url));

        Toast.makeText(this, "Sending article.", Toast.LENGTH_LONG).show();
        nfcAdapter.setNdefPushMessageCallback(this, this);
    }

    /**
     * This method is called when clicked on the Receive Article button
     * When the user clicks on the Receive Article button, it will listen to NFC messages
     * sent from nearby devices
     */
    public void receiveArticle(View view) {
        Toast.makeText(this, "Waiting to receive article.", Toast.LENGTH_LONG).show();
        onResume();
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String data = "";
        if(useOfNFC.equals("article")) {
            WikipediaApp app = WikipediaApp.getInstance();
            Tab currentTab = app.getTabList().get(app.getTabList().size() - 1);
            PageBackStackItem lastTab = currentTab.getBackStack().get(currentTab.getBackStackPosition());
            data = useOfNFC + "," + lastTab.getTitle().getCanonicalUri();
//            return new NdefMessage(new NdefRecord[] { NdefRecord.createUri(url) });
        } else if(useOfNFC.equals("note")) {
            //DO SOMETHING
        }
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", data.getBytes());
        NdefMessage message = new NdefMessage(ndefRecord);
        return message;
    }

    @Override
    public void onResume() {
        Toast.makeText(this, "Scanning.", Toast.LENGTH_LONG).show();
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
        Log.d("TEST", rawData);
        String[] data = rawData.split(",");
        Log.d("TEST2", "data have a size of " + (data.length));
        if(data[0].equals("article")) {
            String url = data[1];
            Log.d("TEST3", url);
            Toast.makeText(this, url, Toast.LENGTH_LONG);
            nfcFrameLayout.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(url);
        }
    }

}
