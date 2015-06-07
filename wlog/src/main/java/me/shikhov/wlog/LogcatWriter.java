package me.shikhov.wlog;

import android.support.annotation.NonNull;

/**
 *
 * Created by Andrew on 07.05.2015.
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
            android.util.Log.println(logLevel, tag, message);
        }
        else
        {
            android.util.Log.println(logLevel, tag, message + "\n" + Log.getStackTraceString(tr));
        }
    }
}
