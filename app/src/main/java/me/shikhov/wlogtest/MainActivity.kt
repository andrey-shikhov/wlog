package me.shikhov.wlogtest

import android.app.Activity
import android.os.Bundle
import me.shikhov.wlog.Log
import java.net.UnknownServiceException
import java.util.*

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

        override fun toString(): String {
            return super.toString()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log[TAG].event(this, "onCreate").release()
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        Log[TAG].event(this, "onResume").r()
        Log[TAG].a(arrayOf(1, 2, 3, 4, 5)).release()
        val list: MutableList<String> = ArrayList()
        list.add("one")
        list.add("two")
        list.add("three")
        Log[TAG].a(list).r()
        val map = HashMap<String, String?>()
        map["one"] = "not two"
        map["two"] = "not three"
        map["three"] = "not four"
        map["four"] = null
        Log[TAG].a(map).r()
        val t: MutableList<String> = LinkedList()
        t.add("singleItem")
        val m: MutableMap<String, Int> = LinkedHashMap()
        m["singleItem"] = 8
        Log[TAG].a(t).r()
        Log[TAG].a(m).r()
        Log[TAG].a("<< arrays >>").r()
        val testArr = intArrayOf(0, 1, 2, 3, 5, 8, 13, 21)
        Log[TAG].a("array: ").a(testArr).r(Log.WARN)

        Log(TAG) {
            +"test message 1"
            +map.toString()
            throwable = UnknownServiceException()
            +"test message 2"
        }
    }

    override fun onPause() {
        super.onPause()
        Log[TAG].event(this, "onPause").release()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log[TAG].event(this, "onDestroy").release()
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}