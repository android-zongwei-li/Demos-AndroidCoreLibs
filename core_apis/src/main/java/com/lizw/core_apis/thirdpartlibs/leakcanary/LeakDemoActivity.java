package com.lizw.core_apis.thirdpartlibs.leakcanary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.lizw.core_apis.R;

import java.lang.reflect.Field;

public class LeakDemoActivity extends AppCompatActivity {
    private static final String TAG = "LeakDemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_demo);

//        testThreadLeak();


        new Thread(new MyRunnable()).start();

//        fixSystemJobService();
    }

    private void fixSystemJobService() {
        try {
            Class<?> resourcesImpleClass = Class.forName("android.content.res.ResourcesImpl");
            Field mAppContext = resourcesImpleClass.getDeclaredField("mAppContext");
            Object f = mAppContext.get(null);
            Log.i(TAG, "fixSystemJobService " + (String) f);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            Log.e(TAG, "fixSystemJobService " + e.getMessage());
        }
    }

    private void testThreadLeak() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Log.i(TAG, "running1");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }).start();
    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                Log.i(TAG, "running1");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}