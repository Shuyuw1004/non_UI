package com.example.non_ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button startButton;
    private volatile boolean stopThread = false;
    private TextView textView;
    class ExampleRunnable implements  Runnable {
        @Override
        public void run() {
            mockFileDownloader();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        startButton = findViewById(R.id.startButton);
    }

    public void mockFileDownloader(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startButton.setText("Downloading...");
            }
        });


        for(int downloadProgress = 0; downloadProgress <= 100; downloadProgress = downloadProgress+10){

            if(stopThread) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startButton.setText("Start");
                        textView.setText("");
                    }
                });
                return;
            }
            else{
                int finalDownloadProgress = downloadProgress;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("Download Progress: " + finalDownloadProgress + "%");
                    }
                });

            }

            Log.d(TAG, "Download Progress: " + downloadProgress + "%");try{
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    }
    public  void startDownload(View view){
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }
    public void stopDownload(View view){
        stopThread = true;
    }
}