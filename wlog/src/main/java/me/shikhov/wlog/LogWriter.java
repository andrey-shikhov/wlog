package me.shikhov.wlog;

/**
 * Basic interface for log writers
 * @see LogcatWriter
 * Created by Andrew on 07.05.2015.
 */
public interface LogWriter
{
    void write(int logLevel, String tag, String message, Throwable tr);
}
