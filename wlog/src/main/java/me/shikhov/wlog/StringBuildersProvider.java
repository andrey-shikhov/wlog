package me.shikhov.wlog;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by Andrew on 11.05.2015.
 */
class StringBuildersProvider
{
    private final List<StringBuilder> reservedStringBuilders = new ArrayList<>();
    private final Object sbMutex = new Object();

    private int reservedCapacity;
    private int maxReservedCapacity;

    StringBuildersProvider()
    {
        maxReservedCapacity = 16384;
    }

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
