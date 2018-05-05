package com.izxjf.liao.keyboardlistener;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

public class MainActivity extends AppCompatActivity {

    private View viewById;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewById = findViewById(R.id.root_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        KeyboardListener keyboardListener = new KeyboardListener(this);
        keyboardListener.setOnKeyboardListener(new KeyboardListener.OnKeyboardListener() {
            @Override
            public void onKeyboardOpened(int keyboardHeight) {
                Log.e("test","open"+keyboardHeight);
            }

            @Override
            public void onKeyboardClose() {
                Log.e("test","close");
            }
        });
    }

}
