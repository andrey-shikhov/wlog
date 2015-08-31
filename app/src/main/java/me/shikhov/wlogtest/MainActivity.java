package me.shikhov.wlogtest;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.shikhov.wlog.Log;


public class MainActivity extends Activity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    class KnownPeople
    {
        class Person
        {
            String name;
            int age;

            @Override
            public String toString()
            {
                return super.toString();
            }
        }

        List<Person> schoolFriends = new ArrayList<>();

        Map<String,Person> universityFriends = new HashMap<>();

        @Override
        public String toString()
        {


            return super.toString();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
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

        List<String> list = new ArrayList<>();
        list.add("one");
        list.add("two");
        list.add("three");

        Log.get(TAG).a(list).r();

        HashMap<String,String> map = new HashMap<>();
        map.put("one", "not two");
        map.put("two", "not three");
        map.put("three", "not four");
        map.put("four", null);

        Log.get(TAG).a(map).r();

        List<String> t = new LinkedList<>();
        t.add("singleItem");

        Map<String, Integer> m = new LinkedHashMap<>();
        m.put("singleItem", 8);

        Log.get(TAG).a(t).r();
        Log.get(TAG).a(m).r();
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
