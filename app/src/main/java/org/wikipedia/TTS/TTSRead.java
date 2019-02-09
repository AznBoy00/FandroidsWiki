package org.wikipedia.TTS;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

//import org.wikipedia.alpha.R;

import org.wikipedia.R;

import java.util.Locale;

import io.reactivex.annotations.Nullable;

///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link TTSRead.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link TTSRead#newInstance} factory method to
// * create an instance of this fragment.
// */
public class TTSRead extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    TextToSpeech toSpeech;
    //EditText editText;
    Button playBTN;
    String TTSTestText;
    int result;

//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment TTSRead.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static TTSRead newInstance(String param1, String param2) {
//        TTSRead fragment = new TTSRead();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//
//        }
//
//        setRetainInstance(true);
//        Log.e("TTS","TTSvr oncreate_TTSR");
//        TTSTestText="This is the test for TTS voice reading.";
//        toSpeech=new android.speech.tts.TextToSpeech(this.getActivity(), new TextToSpeech.OnInitListener() {
//            @Override
//            public void onInit(int status) {
//                if(status== android.speech.tts.TextToSpeech.SUCCESS)
//                {
//                    result=toSpeech.setLanguage(Locale.US);
//                    if (result == TextToSpeech.LANG_MISSING_DATA
//                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                        Log.e("TTS", "This Language is not supported");
//                    }
//                    else {// this is only for test
//                        toSpeech.speak(TTSTestText, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
//                    }
//                }
//                else
//                {
//                    //Toast.makeText(getApplicationContext(),"Feature not supported in your device",Toast.LENGTH_SHORT).show();
//                    Log.e("TTS", "Could not initialize TextToSpeech");
//                }
//            }
//        });
//
//
//
//        toSpeech.speak(TTSTestText, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
//        Log.e("TTS","TTSvr oncreate done_TTSR");
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.e("TTS", "onCreatView");
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ttsread, container, false);
       // View v = inflater.inflate(R.layout.view_article_tab_layout, container, false);

        toSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status== TextToSpeech.SUCCESS)
                {
                    result=toSpeech.setLanguage(Locale.US);
//                    if (result == TextToSpeech.LANG_MISSING_DATA
//                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
//                        Log.e("TTS", "This Language is not supported");
//                    }
//                    else {// this is only for test
//                        toSpeech.speak(TTSTestText, TextToSpeech.QUEUE_FLUSH, null);
//                    }
                }
                else
                {
                    //Toast.makeText(getApplicationContext(),"Feature not supported in your device",Toast.LENGTH_SHORT).show();
                    Log.e("TTS", "Could not initialize TextToSpeech");
                }
            }
        } );

       playBTN = (Button) v.findViewById(R.id.TTS_play);
        //playBTN = (Button) v.findViewById(R.id.article_menu_text_to_speech);
        playBTN.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switch (v.getId())
                {
                    case  R.id.TTS_play:
                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        }
                        else {// this is only for test
                            toSpeech.speak(TTSTestText, TextToSpeech.QUEUE_FLUSH, null);
                        }
                        break;
                    case  R.id.article_menu_text_to_speech:
                        if (result == TextToSpeech.LANG_MISSING_DATA
                                || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "This Language is not supported");
                        }
                        else {// this is only for test
                            toSpeech.speak(TTSTestText, TextToSpeech.QUEUE_FLUSH, null);
                        }
                        break;
                }
            }
        });

        //return inflater.inflate(R.layout.fragment_ttsread, container, false);
        return v;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    public void TTSPlay(String txt){


        Log.e("TTS","TTSvr startplay");
        //result=toSpeech.setLanguage(Locale.US);
        toSpeech.speak(txt, android.speech.tts.TextToSpeech.QUEUE_FLUSH, null);
        Log.e("TTS","TTSvr vplay end");
    }


    public TextToSpeech getMyTTS(){
        return toSpeech;
    }


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
