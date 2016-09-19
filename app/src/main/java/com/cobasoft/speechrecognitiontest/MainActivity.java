package com.cobasoft.speechrecognitiontest;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
            }
        });
    }

    /**
     * Showing google speech input dialog
     */
    private void promptSpeechInput() {
        // Starts an activity that will prompt the user for speech and send it through a speech recognizer.
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        // Use a language model based on free-form speech recognition. Free form for dictation
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        // Extra language, device locale default
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        // Prompt for showing
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            // Starts the dictation activity
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            // if dictation is not supported
            Toast.makeText(getApplicationContext(),
                    "No se soporta dictado",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // receiving our intent result
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null) {
                    // Retrieves the results
                    ArrayList<String> resultTopFive = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    performAction(resultTopFive);
                }
                break;
            }

        }
    }

    private void performAction(ArrayList<String> resultTop) {
        for (String top : resultTop) {
            if (top.toLowerCase().contains("siguiente")) {
                Log.d(TAG, "performAction: FOUND!!!!!!!!!!");
                Intent nextActivityIntent = new Intent(this, NextActivity.class);
                startActivity(nextActivityIntent);
                break;
            }
        }
    }


}
