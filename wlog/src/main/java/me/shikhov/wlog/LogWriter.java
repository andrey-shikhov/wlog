package me.shikhov.wlog;

/**
 * Created by Andrew on 07.05.2015.
 */
public interface LogWriter
{
    void write(int logLevel, String tag, String message, Throwable tr);
}
