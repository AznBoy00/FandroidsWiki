package org.wikipedia.tts;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;


public class TTSVoiceRead extends AppCompatActivity implements TextToSpeech.OnInitListener{


    android.speech.tts.TextToSpeech toSpeech;
    //EditText editText;

    String TTSTestText;
    int result;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TTS","TTSvr oncreate");
        TTSTestText="This is the test for TTS voice reading.";
        toSpeech=new android.speech.tts.TextToSpeech(this, this);
        result=toSpeech.setLanguage(Locale.US);
        toSpeech.speak(TTSTestText, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
        Log.e("TTS","TTSvr oncreate done");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toSpeech!=null)
        {
            toSpeech.stop();
            toSpeech.shutdown();
        }
    }
    @Override
    public void onInit(int status) {
        if(status== android.speech.tts.TextToSpeech.SUCCESS)
        {
            result=toSpeech.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }
            else {
                toSpeech.speak(TTSTestText, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        else
            {
                Toast.makeText(getApplicationContext(),"Feature not supported in your device",Toast.LENGTH_SHORT).show();
            }
    }

    public void TTSPlay(String txt){


        Log.e("TTS","TTSvr startplay");
        result=toSpeech.setLanguage(Locale.US);
        toSpeech.speak(txt, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
        Log.e("TTS","TTSvr vplay end");
    }
}
