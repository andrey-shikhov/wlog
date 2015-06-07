package me.shikhov.wlog;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
class Loggers
{
    private static final Object mutex = new Object();
    private static final Map<Integer, Log> loggersMap = new HashMap<>();

    @NonNull
    static Log getLogger(@NonNull String tag)
    {
        int threadId = android.os.Process.myTid();

        Log logger = loggersMap.get(threadId);

        if(logger == null)
        {
            logger = new Log(tag);

            synchronized (mutex)
            {
                loggersMap.put(threadId, logger);
            }
        }
        else
        {
            logger.setTag(tag);
        }

        return logger;
    }

    static void removeLogger(@NonNull Log log)
    {
        synchronized (mutex)
        {
            loggersMap.values().remove(log);
        }
    }
}
