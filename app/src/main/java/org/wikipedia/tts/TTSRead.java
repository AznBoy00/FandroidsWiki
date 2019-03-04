package org.wikipedia.tts;

import android.net.Uri;

//import org.wikipedia.alpha.R;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link TTSRead.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link TTSRead#newInstance} factory method to
// * create an instance of this fragment.
// */
public class TTSRead{


/*  public void TTSPlay(String txt){
        Log.e("TTS","TTSvr startplay");
        //result=toSpeech.setLanguage(Locale.US);
        t1.speak(txt, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
        Log.e("TTS","TTSvr vplay end");
    }*/


  /*  public TextToSpeech getMyTTS(){
        return t1;
    }
*/

//    public void onInit(int status) {
//        if(status== android.speech.tts.TextToSpeech.SUCCESS||true)
//        {
//            result=toSpeech.setLanguage(Locale.US);
//            if (result == TextToSpeech.LANG_MISSING_DATA
//                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                Log.e("TTS", "This Language is not supported");
//            }
//            else {// this is only for test
//                toSpeech.speak(TTSTestText, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
//            }
//        }
//        else
//        {
//            //Toast.makeText(getApplicationContext(),"Feature not supported in your device",Toast.LENGTH_SHORT).show();
//            Log.e("TTS", "Could not initialize TextToSpeech");
//        }
//    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
