package com.text_to_speech.texttospeech;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.RemoteConference;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextToSpeech tts;
    EditText t1;
    SpeechRecognizer spr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1 = (EditText) findViewById(R.id.editText);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.ENGLISH);
                tts.setPitch(1.5f);
            }
        });
    }

    public void speakText(View v){
        if(t1.getText().toString().length() > 0){
            tts.speak(t1.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
            Button b1 = (Button) v;
            Log.d("Button Text is",b1.getText().toString());
        }
    }

    public void listenForInput(View v){
        spr = SpeechRecognizer.createSpeechRecognizer(this);
        spr.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {
                Log.d("recognition","Speech Input Started");
            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {
                Log.d("recognition","Speech Input Ended");
            }

            @Override
            public void onError(int error) {
                Log.d("recognition","Speech Input Error with Error Code " + error);
            }

            @Override
            public void onResults(Bundle results) {
                Log.d("recognition","Speech Input Completed with results");
                ArrayList<String> msg =
                        results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                t1.setText(msg.get(0));
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                Log.d("recognition","Speech Input completed with Partial results");
            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
        Intent recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"en");
        //recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"com.text_to_speech.texttospeech");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, MainActivity.this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
        spr.startListening(recognizerIntent);
    }

    public void stopListening(View v){
        if(spr != null){
            spr.stopListening();
        }
    }
}
