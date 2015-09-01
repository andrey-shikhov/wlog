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

/**
 * Wrapper for android Log class, controls length of message(4k limit)
 */
public final class LogcatWriter implements LogWriter
{
    private static final LogcatWriter instance = new LogcatWriter();

    @NonNull
    public static LogcatWriter getInstance()
    {
        return instance;
    }

    private LogcatWriter()
    {

    }

    @Override
    public void write(int logLevel, String tag, String message, Throwable tr)
    {
        if(tr == null)
        {
            if(message.length() > 4000)
            {
                int count = message.length()/4000;

                for(int i = 0; i < count; i++)
                {
                    android.util.Log.println(logLevel, tag, message.substring(i*4000,(i+1)*4000));
                }

                android.util.Log.println(logLevel, tag, message.substring(count*4000));
            }
            else
            {
                android.util.Log.println(logLevel, tag, message);
            }
        }
        else
        {
            android.util.Log.println(logLevel, tag, message + "\n" + Log.getStackTraceString(tr));
        }
    }
}
