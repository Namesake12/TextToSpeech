package com.text_to_speech.texttospeech;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Locale;

public class Main2Activity extends AppCompatActivity {
    TextToSpeech textToSpeech;
    SpeechRecognizer speechRecognizer;
    Intent recognizerIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void setUpRecognizerIntent(){
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, Main2Activity.this.getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,5);
    }

    public void processInput(ArrayList<String> msg){
        String reply ="";
        switch (msg.get(0).toString().toLowerCase()){
            case "how are you":
                reply = "I am fine Thank you";
                break;
            case "what is your name":
                reply = "my name is Siri";
                break;
            default:
                reply = "I did not get the command";
                break;
        }
        textToSpeech.speak(reply,TextToSpeech.QUEUE_FLUSH,null);
    }

    public void setUpSpeechRecognizer(){
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                ArrayList<String> msg =
                        results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                processInput(msg);
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
    }

    public void startListeningForInput(View v){
        //Log.d("button","button1 clicked");return;
        setUpSpeechRecognizer();
        setUpRecognizerIntent();
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                textToSpeech.setLanguage(Locale.ENGLISH);
                textToSpeech.setPitch(1.5f);
            }
        });
        speechRecognizer.startListening(recognizerIntent);
    }

    public void stopListeningForInput(View v){
        speechRecognizer.stopListening();
    }
}
