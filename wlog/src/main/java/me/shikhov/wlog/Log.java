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

/**
 * Main class
 * "Without loss of generality"
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

    //region legacy migration methods equals to android.util.Log
    /**
     * <b>LEGACY, FOR MIGRATION PURPOSES</b>
     * Send a {@link #VERBOSE} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @deprecated
     */
    @Deprecated
    public static int v(String tag, String msg) {
        return println(VERBOSE, tag, msg);
    }

    /**
     * Method added for easier log migration, signatures identical to android sdk Log class
     * _____________________________________
     * Send a {@link #VERBOSE} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated
    public static int v(String tag, String msg, Throwable tr) {
        return println(VERBOSE, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * Method added for easier log migration, signatures identical to android sdk Log class
     * _____________________________________
     * Send a {@link #DEBUG} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @Deprecated
    public static int d(String tag, String msg) {
        return println(DEBUG, tag, msg);
    }

    /**
     * Method added for easier log migration, signatures identical to android sdk Log class
     * _____________________________________
     * Send a {@link #DEBUG} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated
    public static int d(String tag, String msg, Throwable tr) {
        return println(DEBUG, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * Method added for easier log migration, signatures identical to android sdk Log class
     * _____________________________________
     * Send an {@link #INFO} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @Deprecated
    public static int i(String tag, String msg) {
        return println(INFO, tag, msg);
    }

    /**
     * Method added for easier log migration, signatures identical to android sdk Log class
     * _____________________________________
     * Send a {@link #INFO} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated
    public static int i(String tag, String msg, Throwable tr) {
        return println(INFO, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * Method added for easier log migration, signatures identical to android sdk Log class
     * _____________________________________
     * Send a {@link #WARN} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @Deprecated
    public static int w(String tag, String msg) {
        return println(WARN, tag, msg);
    }

    /**
     * Method added for easier log migration, signatures identical to android sdk Log class
     * _____________________________________
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated
    public static int w(String tag, String msg, Throwable tr) {
        return println(WARN, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * Method added for easier log migration, signatures identical to android sdk Log class
     * _____________________________________
     * Send a {@link #WARN} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param tr An exception to log
     */
    @Deprecated
    public static int w(String tag, Throwable tr) {
        return println(WARN, tag, getStackTraceString(tr));
    }

    /**
     * Method added for easier log migration, signatures identical to android sdk Log class
     * _____________________________________
     * Send an {@link #ERROR} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
    @Deprecated
    public static int e(String tag, String msg) {
        return println(ERROR, tag, msg);
    }

    /**
     * Method added for easier log migration, signatures identical to android sdk Log class
     * _____________________________________
     * Send a {@link #ERROR} log message and log the exception.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    @Deprecated
    public static int e(String tag, String msg, Throwable tr) {
        return println(ERROR, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     */
    @Deprecated
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

    /**
     * Sets up log writer which will be used in Log instances.<br>
     * Previously created Log objects will not be changed.
     * @param logWriter
     */
    public static void setGlobalLogWriter(@NonNull LogWriter logWriter)
    {
        Log.GLOBAL_LOGWRITER = logWriter;
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

    private String tag;

    private final StringBuilder builder;

    private LogWriter logWriter = GLOBAL_LOGWRITER;

    private int logLevel = android.util.Log.DEBUG;

    private Throwable throwableToLog;

    private boolean disposed;

    Log(@NonNull String tag)
    {
        this.builder = stringBuildersProvider.acqireStringBuilder();
        this.logWriter = GLOBAL_LOGWRITER;
        this.tag = tag;
    }

    /**
     * @see LogcatWriter
     * @param logWriter
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

        stringBuildersProvider.releaseStringBuilder(builder);

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
        builder.append(object.getClass().getSimpleName())
                .append("@").append(object.hashCode())
                .append(" ").append(event);

        return this;
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

        if(builder.length() > 0)
        {
            message = builder.toString();
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

        if(builder.length() > 0)
        {
            builder.delete(0, builder.length());
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
        builder.append(value);
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
        builder.append(value);
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
        builder.append(value);
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
        builder.append(value);
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
        builder.append(value);
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
        builder.append(value == null ? "null" : value);
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

        if(radix == 10)
        {
            builder.append(value);
        }
        else
        {
            builder.append(Integer.toString(value, radix));
        }

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
        builder.append(object);

        return this;
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
        builder.append(string);

        return this;
    }

    @NonNull
    public Log append(@Nullable Object[] array)
    {
        return a(array);
    }

    @NonNull
    public Log a(@Nullable Object[] array)
    {
        checkDisposed();
        if(array == null)
            builder.append("null");
        else
        {
            ArrayFormatter.format(ArrayFormatter.getDefaultFormatter(),
                    builder, array);
        }

        return this;
    }

    //endregion


    private void checkDisposed()
    {
        if(disposed)
        {
            throw new IllegalStateException("Log " + tag + " is disposed, usage after release() call is prohibited");
        }
    }

    /**
     * Shortcut to {@link #a(String)}.{@link #i()}<br>
     * Logs string and set logLevel to INFO
     * @param string
     *      string to log
     * @return
     *      this object
     */
    public Log i(@NonNull String string)
    {
        return a(string).i();
    }

    @NonNull
    public Log i()
    {
        logLevel = android.util.Log.INFO;
        return this;
    }

    @NonNull
    public Log info()
    {
        return i();
    }

    @NonNull
    public Log debug()
    {
        return d();
    }

    @NonNull
    public Log d()
    {
        logLevel = android.util.Log.DEBUG;
        return this;
    }

    @NonNull
    public Log warn()
    {
        return w();
    }

    @NonNull
    public Log w()
    {
        logLevel = android.util.Log.WARN;
        return this;
    }
}
