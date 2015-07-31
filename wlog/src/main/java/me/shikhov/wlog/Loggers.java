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
    static Log getLogger()
    {
        int threadId = android.os.Process.myTid();

        Log logger = loggersMap.get(threadId);

        if(logger == null)
        {
            logger = new Log("LOG");

            synchronized (mutex)
            {
                loggersMap.put(threadId, logger);
            }
        }

        return logger;
    }

    @NonNull
    static Log getLogger(@NonNull String tag)
    {
        Log logger = getLogger();
        logger.setTag(tag);

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