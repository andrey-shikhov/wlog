/*******************************************************************************
 * Copyright 2015 Andrew Shikhov.
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
 *******************************************************************************/
package me.shikhov.wlog;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Class holds StringBuilder objects recently used for loggin in purposes to reuse them
 * for next log operations.<br>
 * Size of cached builders can be changed by setting system property "wlog.reservedCapacity".
 * Size set in characters, default value is 16384
 */
class StringBuildersProvider
{
    private final List<StringBuilder> reservedStringBuilders = new ArrayList<>();
    private final Object sbMutex = new Object();

    private int reservedCapacity;
    private int maxReservedCapacity;

    StringBuildersProvider()
    {
        if(BuildConfig.DEBUG)
        {
            android.util.Log.d("wlog", "wlog.reservedCapacity=" + System.getenv("wlog.reservedCapacity"));
        }

        maxReservedCapacity = 16384;
    }

    /**
     * Returns stringbuilder for logging, for new objects capacity is 128 chars.
     * @return
     *      new or used StringBuilder object, so capacity varies from object to object
     */
    @NonNull
    StringBuilder acqireStringBuilder()
    {
        synchronized (sbMutex)
        {
            if(!reservedStringBuilders.isEmpty())
            {
                StringBuilder sb = reservedStringBuilders.remove(reservedStringBuilders.size() - 1);

                reservedCapacity -= sb.capacity();

                return sb;
            }
        }

        return new StringBuilder(128);
    }

    /**
     * This method called by {@link Log} class to return StringBuilder after Log object was disposed
     * to optimize memory allocations.
     * @param stringBuilder
     */
    void releaseStringBuilder(@NonNull StringBuilder stringBuilder)
    {
        synchronized (sbMutex)
        {
            if(reservedCapacity + stringBuilder.capacity() < maxReservedCapacity)
            {
                reservedStringBuilders.add(stringBuilder);
                reservedCapacity += stringBuilder.capacity();
            }
        }
    }
}
