package com.youknow.handlerleak;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

public class NonLeakActivity extends AppCompatActivity {

    private NonLeakHandler handler = new NonLeakHandler();

    private static final class NonLeakHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d("TEST", "[MemoryLeak] handleMessage");
        }
    }

    private static final Runnable runnable = new Runnable() {
        @Override
        public void run() {}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEST", "[MemoryLeak] onCreate");

        handler.postDelayed(runnable, 10_000);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TEST", "[MemoryLeak] onDestroy");
        handler.removeCallbacksAndMessages(null);
    }
}


