package me.shikhov.wlogtest;

import android.app.Activity;
import android.os.Bundle;

import me.shikhov.wlog.Log;


public class MainActivity extends Activity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate, compatible way");
        Log.get(TAG).event(this, "onCreate").i().release();

        setContentView(R.layout.activity_main);


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        Log.get(TAG).event(this, "onResume")
                .f()
                .a(new Integer[]{1,2,3,4,5})
                .release();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        Log.get(TAG).event(this, "onPause").release();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        Log.get(TAG).event(this, "onDestroy").release();
    }
}
