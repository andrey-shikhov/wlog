package me.shikhov.wlog;

/**
 *
 * Created by Andrew on 07.05.2015.
 */
public class LogcatWriter implements LogWriter
{
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
