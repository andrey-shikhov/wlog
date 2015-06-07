package me.shikhov.wlog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * "Without loss of generality"
 * Created by Andrew on 06.05.2015.
 */
public class Log
{
    //region logLevel constants
    /**
     * Priority constant for the println method; use Log.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Priority constant for the println method; use Log.d.
     */
    public static final int DEBUG = 3;

    /**
     * Priority constant for the println method; use Log.i.
     */
    public static final int INFO = 4;

    /**
     * Priority constant for the println method; use Log.w.
     */
    public static final int WARN = 5;

    /**
     * Priority constant for the println method; use Log.e.
     */
    public static final int ERROR = 6;

    /**
     * Priority constant for the println method.
     */
    public static final int ASSERT = 7;
    //endregion

    //region legacy migration methods equals to android.util.Log
    /**
     * Method added for easier log migration, signatures identical to android sdk Log class
     * _____________________________________
     * Send a {@link #VERBOSE} log message.
     * @param tag Used to identify the source of a log message.  It usually identifies
     *        the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     */
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
    public static int e(String tag, String msg, Throwable tr) {
        return println(ERROR, tag, msg + '\n' + getStackTraceString(tr));
    }

    /**
     * Handy function to get a loggable stack trace from a Throwable
     * @param tr An exception to log
     */
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
    public static int println(int priority, String tag, String msg) {
        return android.util.Log.println(priority, tag, msg);
    }
    //endregion

    private static LogWriter GLOBAL_LOGWRITER = LogcatWriter.getInstance();

    public static void setGlobalLogWriter(@NonNull LogWriter logWriter)
    {
        Log.GLOBAL_LOGWRITER = logWriter;
    }

    @NonNull
    private static final StringBuildersProvider stringBuildersProvider = new StringBuildersProvider();

    /**
     * The only way to receive log object.<br/>
     * This method is thread safe.<br/>
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
        if(builder.length() > 0)
        {
            builder.setLength(0);
        }

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
    public Log throwable(@NonNull Throwable throwable)
    {
        return t(throwable);
    }

    public Log event(@NonNull Object object, @NonNull String event)
    {
        builder.append(object.getClass().getSimpleName()).append("@").append(object.hashCode()).append(" ")
                .append(event);

        return this;
    }

    /**
     * Shortcut for {@link #flush()} method. <br/>
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
            if(throwableToLog != null)
            {
                builder.append("\n");
                builder.append(getStackTraceString(throwableToLog));

                throwableToLog = null;
            }

            message = builder.toString();
        }
        else
        {
            message = "";
        }

        logWriter.write(logLevel, tag, message, throwableToLog);

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
    @NonNull
    public Log append(boolean value)
    {
        return a(value);
    }

    @NonNull
    public Log a(boolean value)
    {
        checkDisposed();
        builder.append(value);
        return this;
    }

    @NonNull
    public Log append(char value)
    {
        return a(value);
    }

    @NonNull Log a(char value)
    {
        checkDisposed();
        builder.append(value);
        return this;
    }

    @NonNull
    public Log append(long value)
    {
        return a(value);
    }

    @NonNull Log a(long value)
    {
        checkDisposed();
        builder.append(value);
        return this;
    }

    @NonNull
    public Log append(float value)
    {
        return a(value);
    }

    @NonNull Log a(float value)
    {
        checkDisposed();
        builder.append(value);
        return this;
    }

    @NonNull
    public Log append(double value)
    {
        return a(value);
    }

    @NonNull Log a(double value)
    {
        checkDisposed();
        builder.append(value);
        return this;
    }

    @NonNull
    public Log append(char[] value)
    {
        return a(value);
    }

    @NonNull Log a(char[] value)
    {
        checkDisposed();
        builder.append(value);
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

    @NonNull
    public Log append(@Nullable Object object)
    {
        return a(object);
    }

    public Log a(@Nullable Object object)
    {
        checkDisposed();
        builder.append(object);

        return this;
    }

    @NonNull
    public Log a(@NonNull String string)
    {
        checkDisposed();
        builder.append(string);

        return this;
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

    public Log i(@NonNull String string)
    {
        return a(string).i();
    }

    public Log i()
    {
//        logWriter.i(tag, builder.toString());

        builder.delete(0, builder.length());

        return this;
    }

    @NonNull
    public Log info()
    {
        logLevel = android.util.Log.INFO;
        return this;
    }

    @NonNull
    public Log debug()
    {
        return d();
    }

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

    public Log w()
    {
        logLevel = android.util.Log.WARN;
        return this;
    }
}
