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
import android.support.annotation.Nullable;

import java.util.Collection;
import java.util.Map;

/**
 * Main entry for this library.
 * "Without loss of generality"
 * @see #get(String)
 * @see #r()
 * @see #append(String)
 */
@SuppressWarnings("unused")
public class Log
{
    //region logLevel constants
    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = android.util.Log.VERBOSE;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = android.util.Log.DEBUG;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = android.util.Log.INFO;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = android.util.Log.WARN;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = android.util.Log.ERROR;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = android.util.Log.ASSERT;
    //endregion

    //region shortcut methods to log simple string/throwable statements
    /**
     * Send a {@link #VERBOSE} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */

    public static void v(String tag, String msg)
    {
        get(tag).a(msg).v().r();
    }

    /**
     * Send a {@link #VERBOSE} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void v(String tag, String msg, Throwable tr)
    {
        get(tag).a(msg).t(tr).v().r();
    }

    /**
     * Send a {@link #DEBUG} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg)
    {
        get(tag).a(msg).d().r();
    }

    /**
     * Send a {@link #DEBUG} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void d(String tag, String msg, Throwable tr)
    {
        get(tag).a(msg).t(tr).d().r();
    }

    /**
     * Send an {@link #INFO} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg)
    {
        get(tag).a(msg).i().r();
    }

    /**
     * Send a {@link #INFO} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void i(String tag, String msg, Throwable tr)
    {
        get(tag).a(msg).t(tr).i().r();
    }

    /**
     * Send a {@link #WARN} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg)
    {
        get(tag).a(msg).w().r();
    }

    /**
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void w(String tag, String msg, Throwable tr)
    {
        get(tag).a(msg).t(tr).w().r();
    }

    /**
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    public static void w(String tag, Throwable tr)
    {
        get(tag).t(tr).w().r();
    }

    /**
     * Send an {@link #ERROR} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */

    public static void e(String tag, String msg)
    {
        get(tag).a(msg).e().r();
    }

    /**
     * Send a {@link #ERROR} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void e(String tag, String msg, Throwable tr)
    {
        get(tag).a(msg).t(tr).e().r();
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     * @return
     *      String representation of stack trace
     */
    @NonNull
    public static String getStackTraceString(Throwable tr) {
        return android.util.Log.getStackTraceString(tr);
    }

    /**
     * Low-level logging call.
     * @param priority The priority/type of this log message
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @return The number of bytes written.
     */
    @Deprecated
    public static int println(int priority, String tag, String msg) {
        return android.util.Log.println(priority, tag, msg);
    }
    //endregion

    private static LogWriter GLOBAL_LOGWRITER = LogcatWriter.getInstance();

    private static int DEFAULT_LOG_LEVEL = DEBUG;

    private static String NEW_LINE = "\n";

    static
    {
        String logLevel = System.getProperty("wlog.logLevel");

        if(logLevel != null)
        {
            android.util.Log.println(android.util.Log.INFO, "wlog", "logLevel: " + logLevel);

            if(logLevel.equals("verbose"))
            {
                DEFAULT_LOG_LEVEL = VERBOSE;
            }
            else
            {
                if(logLevel.equals("debug"))
                {
                    DEFAULT_LOG_LEVEL = DEBUG;
                }
                else
                {
                    if(logLevel.equals("info"))
                    {
                        DEFAULT_LOG_LEVEL = INFO;
                    }
                    else
                    {
                        if(logLevel.equals("error"))
                        {
                            DEFAULT_LOG_LEVEL = ERROR;
                        }
                    }
                }
            }
        }

        String newLine = System.getProperty("wlog.newLine");

        if(newLine != null)
        {
            NEW_LINE = newLine;
        }
    }

    /**
     * Sets up log writer which will be used in Log instances.<br>
     * Previously created Log objects will not be changed.
     * @param logWriter
     *      new logWriter to replace current writer, default is {@link LogcatWriter}
     */
    public static void setGlobalLogWriter(@NonNull LogWriter logWriter)
    {
        Log.GLOBAL_LOGWRITER = logWriter;
    }

    public static void setDefaultLogLevel(int logLevel)
    {
        DEFAULT_LOG_LEVEL = logLevel;
    }

    @NonNull
    private static final StringBuildersProvider stringBuildersProvider = new StringBuildersProvider();

    /**
     * The only way to receive log object.<br>
     * This method is thread safe.<br>
     * In case of calling this method from the thread which is already has active(not released) Log object
     * then that object returned with changed tag, previous pushed data will be automatically flushed.
     * @param tag tag for log object
     * @return Log object with tag set and ready to use.
     */
    @NonNull
    public static Log get(@NonNull final String tag)
    {
        return Loggers.getLogger(tag);
    }

    @NonNull
    public static Log get()
    {
        return Loggers.getLogger();
    }

    private final StringJuggler stringJuggler;

    private String tag;

    private LogWriter logWriter;

    private int logLevel;

    private Throwable throwableToLog;

    private boolean disposed;

    Log(@NonNull String tag)
    {
        this.stringJuggler = new StringJuggler(stringBuildersProvider.acqireStringBuilder());
        this.logWriter = GLOBAL_LOGWRITER;
        this.tag = tag;
        logLevel = DEFAULT_LOG_LEVEL;
    }

    /**
     * @see LogcatWriter
     * @param logWriter
     *      log writer, responsible for actual log output
     */
    public void setLogWriter(@NonNull LogWriter logWriter)
    {
        this.logWriter = logWriter;
    }

    void setTag(@NonNull String tag)
    {
        if(!this.tag.equals(tag))
        {
            flush();
            this.tag = tag;
        }
    }

    /**
     * Shortcut to release Log object
     * @see #release()
     */
    public void r()
    {
        f();

        stringBuildersProvider.releaseStringBuilder(stringJuggler.getStringBuilder());

        Loggers.removeLogger(this);
        disposed = true;
    }

    /**
     * Disposes this log object and returns underlying stringbuilder to the pool.
     * @see #r()
     */
    public void release()
    {
        r();
    }

    /**
     * Shortcut for {@link #throwable(Throwable)}
     * @param throwable throwable to log
     * @return this Log object
     */
    public Log t(@NonNull Throwable throwable)
    {
        if(this.throwableToLog != null)
            flush();

        this.throwableToLog = throwable;

        return this;
    }

    /**
     * Adds throwable object to current log statement.
     * Side effect: if current Logger already had set another throwable,
     * {@link #flush()} will be called internally.
     * @see #t(Throwable)
     * @param throwable throwable to log
     * @return this Log object
     */
    @NonNull
    public Log throwable(@NonNull Throwable throwable)
    {
        return t(throwable);
    }

    /**
     * Special case log:<br>
     * [simpleclassname@hash event]<br>
     * For example: MainActivity@4fad3412 onCreate<br>
     * Convenient way to log lifecycle of object(activity,fragment, etc)
     * @param object
     *          object to log
     * @param event
     *          event raised by object
     * @return
     *          this log object
     */
    @NonNull
    public Log event(@NonNull Object object, @NonNull String event)
    {
        stringJuggler.append(object.getClass().getSimpleName())
                .append("@").append(Integer.toHexString(object.hashCode()))
                .append(" ").append(event);

        return this;
    }

    /**
     * Appends newLine sequence to current statement.<br>
     *     newLine sequence can be configured at startup by setting system property
     *     wlog.newLine, default value is \n
     * @return
     *      this object
     */
    @NonNull
    public Log nl()
    {
        return a(NEW_LINE);
    }

    /**
     * Appends newLine sequence to current statement.<br>
     *     newLine sequence can be configured at startup by setting system property
     *     wlog.newLine, default value is \n
     * @return
     *      this object
     */
    @NonNull
    public Log newLine()
    {
        return nl();
    }

    /**
     * Shortcut for {@link #flush()} method. <br>
     * Flushes data to logWriter.
     * @see #flush()
     * @return this object.
     */
    @NonNull
    public Log f()
    {
        String message;

        if(stringJuggler.hasData())
        {
            message = stringJuggler.getString();
        }
        else
        {
            message = "";
        }

        if(message.length() > 0 || throwableToLog != null)
        {
            logWriter.write(logLevel, tag, message, throwableToLog);

            throwableToLog = null;
        }

        if(stringJuggler.hasData())
        {
            stringJuggler.clear();
        }

        throwableToLog = null;

        return this;
    }

    /**
     * Flushes log(if any data written or throwable was set) to the current LogWriter(typically LogCat)
     * @see #f()
     * @see #setLogWriter(LogWriter)
     * @see #setGlobalLogWriter(LogWriter)
     * @return this object
     */
    @NonNull
    public Log flush()
    {
        return f();
    }

    //region append methods

    /**
     * Appends boolean value to current log statement
     * @param value
     *      value to log
     * @return
     *      this object
     * @see #a(boolean)
     */
    @NonNull
    public Log append(boolean value)
    {
        return a(value);
    }

    /**
     * Appends boolean value to current log statement
     * @param value
     *      boolean value to log
     * @return
     *      this object
     */
    @NonNull
    public Log a(boolean value)
    {
        checkDisposed();
        stringJuggler.append(Boolean.toString(value));
        return this;
    }

    /**
     * Appends char value to current log statement
     * @param value
     *      char value to log
     * @return
     *      this object
     * @see #a(char)
     */
    @NonNull
    public Log append(char value)
    {
        return a(value);
    }

    /**
     * Appends char value to current log statement
     * @param value
     *      char value to log
     * @return
     *      this object
     */
    @NonNull
    public Log a(char value)
    {
        checkDisposed();
        stringJuggler.append(Character.toString(value));
        return this;
    }

    /**
     * Appends long value to current log statement
     * @param value
     *      long value to log
     * @return
     *      this object
     * @see #a(long)
     */
    @NonNull
    public Log append(long value)
    {
        return a(value);
    }

    /**
     * Appends long value to current log statement
     * @param value
     *      long value to log
     * @return
     *      this object
     * @see #a(long)
     */
    @NonNull
    public Log a(long value)
    {
        checkDisposed();
        stringJuggler.append(Long.toString(value));
        return this;
    }

    /**
     * Appends float value to current log statement
     * @param value
     *      float value to log
     * @return
     *      this object
     * @see #a(float)
     */
    @NonNull
    public Log append(float value)
    {
        return a(value);
    }

    /**
     * Appends float value to current log statement
     * @param value
     *      float value to log
     * @return
     *      this object
     */
    @NonNull
    public Log a(float value)
    {
        checkDisposed();
        stringJuggler.append(Float.toString(value));
        return this;
    }

    /**
     * Appends double value to current log statement
     * @param value
     *      double value to log
     * @return
     *      this object
     * @see #a(double)
     */
    @NonNull
    public Log append(double value)
    {
        return a(value);
    }

    /**
     * Appends double value to current log statement
     * @param value
     *      double value to log
     * @return
     *      this object
     */
    @NonNull
    public Log a(double value)
    {
        checkDisposed();
        stringJuggler.append(Double.toString(value));
        return this;
    }

    /**
     * Appends char[] value to current log statement
     * @param value
     *      char[] value to log, can be null
     * @return
     *      this object
     * @see #a(char[])
     */
    @NonNull
    public Log append(@Nullable char[] value)
    {
        return a(value);
    }

    /**
     * Appends char[] value to current log statement
     * @param value
     *      char[] value to log, can be null
     * @return
     *      this object
     */
    @NonNull
    public Log a(@Nullable char[] value)
    {
        checkDisposed();
        stringJuggler.append(value == null ? "null" : String.valueOf(value));
        return this;
    }

    @NonNull
    public Log append(int value)
    {
        return a(value, 10);
    }

    @NonNull
    public Log a(int value)
    {
        return a(value, 10);
    }

    @NonNull
    public Log append(int value, int radix)
    {
        return a(value, radix);
    }

    @NonNull
    public Log a(int value, int radix)
    {
        checkDisposed();

        stringJuggler.append(Integer.toString(value, radix));

        return this;
    }

    /**
     * Appends {@code Object#toString} to current log statement
     * @param object
     *      object to log, can be null
     * @return
     *      this object
     * @see #a(Object)
     */
    @NonNull
    public Log append(@Nullable Object object)
    {
        return a(object);
    }

    /**
     * Appends {@code Object#toString} to current log statement
     * @param object
     *      object to log, can be null
     * @return
     *      this object
     */
    @NonNull
    public Log a(@Nullable Object object)
    {
        checkDisposed();
        stringJuggler.append(object == null ? "null" : object.toString());

        return this;
    }

    @NonNull
    public <T> Log a(@Nullable Collection<T> collection)
    {
        checkDisposed();

        SequenceFormatter.format(SequenceFormatter.getDefaultFormatter(),
                stringJuggler.getStringBuilder(),
                collection);

        return this;
    }

    @NonNull
    public <T> Log append(@Nullable Collection<T> collection)
    {
        return a(collection);
    }

    @NonNull
    public <T,V> Log a(@Nullable Map<T,V> map)
    {
        checkDisposed();

        SequenceFormatter.format(SequenceFormatter.getDefaultFormatter(),
                stringJuggler.getStringBuilder(),
                map);

        return this;
    }

    @NonNull
    public <T,V> Log append(@Nullable Map<T,V> map)
    {
        return a(map);
    }

    @NonNull
    public Log append(@NonNull String string)
    {
        return a(string);
    }

    @NonNull
    public Log a(@NonNull String string)
    {
        checkDisposed();
        stringJuggler.append(string);

        return this;
    }

    @NonNull
    public <T> Log append(@Nullable T[] array)
    {
        return a(array);
    }

    @NonNull
    public <T> Log a(@Nullable T[] array)
    {
        checkDisposed();
        if(array == null)
            stringJuggler.append("null");
        else
        {
            SequenceFormatter.format(SequenceFormatter.getDefaultFormatter(),
                    stringJuggler.getStringBuilder(), array);
        }

        return this;
    }

    /**
     * Appends array of objects with specified SequenceFormatter
     * @param array
     *      array of objects
     * @param formatter {@link SequenceFormatter}
     * @param <T> any type allowed
     * @return
     *      this object
     */
    @NonNull
    public <T> Log a(@Nullable T[] array, @NonNull SequenceFormatter formatter)
    {
        checkDisposed();
        if(array == null)
            stringJuggler.append("null");
        else
        {
            SequenceFormatter.format(formatter,
                    stringJuggler.getStringBuilder(), array);
        }

        return this;
    }

    /**
     * Adds simplified class name to log statement, <b>not Object itself!</b><br>
     * NOTE: anonymous classes should override {@code toString()} method which should return
     * recognizable string
     * @param object
     *      object which class name will be logged
     * @return
     *      this object
     */
    @NonNull
    public Log c(@NonNull Object object)
    {
        Class klass = object.getClass();

        String className = klass.getSimpleName();

        if(className.length() == 0)
            className = klass.toString();

        if(className.indexOf('$') != -1)
        {
            className = object.toString();
            className = className.substring(className.lastIndexOf('.') + 1);
        }

        return a(className);
    }

    //endregion


    /**
     * Checks if logger is disposed(underlying StringBuilder returned to pool) and prevents
     * any manipulation if logger in this state by throwing IllegalStateException
     */
    private void checkDisposed()
    {
        if(disposed)
        {
            throw new IllegalStateException("Log " + tag + " is disposed, usage after release() call is prohibited");
        }
    }

    /**
     * Changes current logLevel to {@link #VERBOSE}
     * @return
     *      this object
     */
    public Log verbose()
    {
        return v();
    }

    /**
     * Changes current logLevel to {@link #VERBOSE}
     * @return
     *      this object
     */
    public Log v()
    {
        logLevel = VERBOSE;
        return this;
    }

    /**
     * Sets logLvel to {@link #DEBUG} for current logger
     * @return
     *      this object
     */
    @NonNull
    public Log debug()
    {
        return d();
    }

    /**
     * Sets logLevel to {@link #DEBUG} for current logger
     * @return
     *      this object
     */
    @NonNull
    public Log d()
    {
        logLevel = android.util.Log.DEBUG;
        return this;
    }

    /**
     * Adds string to current statement and changes logLevel to {@link #DEBUG}<br>
     * Shortcut for a(msg).d()
     * @param msg
     *      message to log
     * @return
     *      this object
     */
    @NonNull
    public Log d(String msg)
    {
        return a(msg).d();
    }

    /**
     * Sets logLevel to {@link #INFO} for current logger
     * @return
     *      this object
     */
    @NonNull
    public Log info()
    {
        return i();
    }

    /**
     * Logs string and set logLevel to INFO
     * Shortcut to {@link #a(String)}.{@link #i()}<br>
     * @param string
     *      string to log
     * @return
     *      this object
     */
    public Log i(@NonNull String string)
    {
        return a(string).i();
    }

    /**
     * Sets logLevel to {@link #INFO} for current logger
     * @return
     *      this object
     */
    @NonNull
    public Log i()
    {
        logLevel = android.util.Log.INFO;
        return this;
    }


    /**
     * Sets logLevel to {@link #WARN} for current logger
     * @return
     *      this object
     */
    @NonNull
    public Log warn()
    {
        return w();
    }

    /**
     * Sets logLevel to {@link #WARN} for current logger
     * @return
     *      this object
     */
    @NonNull
    public Log w()
    {
        logLevel = android.util.Log.WARN;
        return this;
    }

    /**
     * Sets logLevel to {@link #ERROR} for current logger
     * @return
     *      this object
     */
    @NonNull
    public Log error()
    {
        return e();
    }

    /**
     * Sets logLevel to {@link #ERROR} for current logger
     * @return
     *      this object
     */
    @NonNull
    public Log e()
    {
        logLevel = ERROR;
        return this;
    }

    /**
     * Adds string to current statement and changes logger's logLevel to {@link #ERROR}<br>
     * Equal to a(msg).e()
     * @param msg message to log
     * @return
     *      this object
     */
    @NonNull
    public Log e(String msg)
    {
        return a(msg).e();
    }

}
