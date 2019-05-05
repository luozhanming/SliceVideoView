package com.example.slicevideoview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.library.SliceVideoView;

public class MainActivity extends AppCompatActivity {

    SliceVideoView sliceVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sliceVideoView = findViewById(R.id.slicevideoview);
        sliceVideoView.post(new Runnable() {
            @Override
            public void run() {
                sliceVideoView.changeSliceCount(6);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
