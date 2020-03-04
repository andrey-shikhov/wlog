/*******************************************************************************
 * Copyright 2015-2020 Andrew Shikhov.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.shikhov.wlog

import android.util.Log
import java.util.*

/**
 * Class holds StringBuilder objects recently used for log purposes to reuse them
 * for next log operations.<br></br>
 * Size of cached builders can be changed by setting system property "wlog.reservedCapacity".
 * Size set in characters, default value is 16384
 */
internal class StringBuildersProvider {
    private val reservedStringBuilders: MutableList<StringBuilder> = ArrayList()
    private val sbMutex = Any()
    private var reservedCapacity = 0
    private val maxReservedCapacity: Int

    /**
     * Returns stringbuilder for logging, for new objects capacity is 128 chars.
     * @return
     * new or used StringBuilder object, so capacity varies from object to object
     */
    fun acqireStringBuilder(): StringBuilder {
        synchronized(sbMutex) {
            if (reservedStringBuilders.isNotEmpty()) {
                val sb = reservedStringBuilders.removeAt(reservedStringBuilders.size - 1)
                reservedCapacity -= sb.capacity()
                return sb
            }
        }
        return StringBuilder(128)
    }

    /**
     * This method called by [Log] class to return StringBuilder after Log object was disposed
     * to optimize memory allocations.
     * @param stringBuilder
     */
    fun releaseStringBuilder(stringBuilder: StringBuilder) {
        synchronized(sbMutex) {
            if (reservedCapacity + stringBuilder.capacity() < maxReservedCapacity) {
                reservedStringBuilders.add(stringBuilder)
                reservedCapacity += stringBuilder.capacity()
            }
        }
    }

    init {
        if (BuildConfig.DEBUG) {
            Log.d("wlog", "wlog.reservedCapacity=" + System.getenv("wlog.reservedCapacity"))
        }
        maxReservedCapacity = 16384
    }
}