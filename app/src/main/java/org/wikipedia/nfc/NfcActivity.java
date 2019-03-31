package org.wikipedia.nfc;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.wikipedia.R;

public class NfcActivity extends Activity implements NfcAdapter.CreateNdefMessageCallback {

    NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);

        Button buttonSend = findViewById(R.id.button_share);
        Button buttonReceive = findViewById(R.id.button_receive);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "Your device does not support NFC", Toast.LENGTH_LONG).show();
            // Will return to previous page if NFC is not supported
            finish();
            return;
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC to use this feature",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is called when clicked on the Share Article button
     * When the user clicks on the Share Article button, it will send the link via NFC
     */
    public void sendArticle(View view) {
        Toast.makeText(this, "Sending article....",
                Toast.LENGTH_LONG).show();
        nfcAdapter.setNdefPushMessageCallback(this, this);
    }

    /**
     * This method is called when clicked on the Receive Article button
     * When the user clicks on the Receive Article button, it will listen to NFC messages
     * sent from nearby devices
     */
    public void receiveArticle(View view) {
        Toast.makeText(this, "Waiting to receive article....",
                Toast.LENGTH_LONG).show();
        onResume();
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        String url = "REPLACE THIS TEXT WITH WIKIPEDIA ARTICLE'S URL";
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", url.getBytes());
        NdefMessage message = new NdefMessage(ndefRecord);
        return message;
    }

    @Override
    public void onResume() {
        Toast.makeText(this, "Scanning.....",
                Toast.LENGTH_LONG).show();
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
        Toast.makeText(this, new String(nfcMessage.getRecords()[0].getPayload()), Toast.LENGTH_LONG);
    }

}
