package me.shikhov.wlogtest

import android.app.Activity
import android.os.Bundle
import me.shikhov.wlog.Log
import java.net.UnknownServiceException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap
import kotlin.concurrent.thread

class MainActivity : Activity() {

    inner class KnownPeople {

        inner class Person {
            var name: String? = null
            var age = 0
            override fun toString(): String {
                return super.toString()
            }
        }

        val schoolFriends: MutableList<Person> = ArrayList()

        var universityFriends: Map<String, Person> = HashMap()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log(TAG).event(this, "onCreate").r()
        setContentView(R.layout.activity_main)

        thread {
            Log("THREAD").event(this, "onStart").r()
            Log("THREAD", logLevel = Log.WARN) {
                line(nlBefore = true)
                +"TEXT"
                "PAIR_KEY".."PAIR_VALUE"
                "PAIR_KEY2"..15
                line('*', nlBefore = true)
            }
            Log("THREAD").event(this, "onEnd").r()
        }
    }

    val someProp = "hello!"

    override fun onResume() {
        super.onResume()
        Log(TAG).event(this, "onResume").r()

        Log(TAG).a(arrayOf(1, 2, 3, 4, 5)).r()

        val list: MutableList<String> = ArrayList()
        list += "one"
        list += "two"
        list += "three"

        Log(TAG).a(list).r()

        val map = HashMap<String, String?>()
        map["one"] = "not two"
        map["two"] = "not three"
        map["three"] = "not four"
        map["four"] = null
        Log(TAG).a(map).r()

        val t = LinkedList<String>()
        t.add("singleItem")
        val m: MutableMap<String, Int> = LinkedHashMap()
        m["singleItem"] = 8
        Log(TAG).a(t).r()
        Log(TAG).a(m).r()
        Log(TAG).a("<< arrays >>").r()
        val testArr = intArrayOf(0, 1, 2, 3, 5, 8, 13, 21)
        Log(TAG).a("array: ").a(testArr).r(Log.WARN)

        Log(TAG) {
            line()
            throwable = UnknownServiceException()
            +"test message 1"
            +map
            +testArr
            +"test message 2"
            +::someProp
            line(char = '*')
        }

        Log(TAG).a("property: ").a(::someProp).r(Log.INFO)

        Log(TAG).a("onResume end").r()
    }

    override fun onPause() {
        super.onPause()
        Log(TAG).event(this, "onPause").r()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log(TAG).event(this, "onDestroy").r()
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}