/*******************************************************************************
 * Copyright 2015-2020 Andrew Shikhov.
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
 */
package me.shikhov.wlog

import java.lang.UnsupportedOperationException
import kotlin.reflect.KProperty0

/**
 * Main entry for this library.
 * "Without loss of generality"
 * @see .get
 * @see .r
 * @see .append
 */
class Log internal constructor(tag: String, separator: CharSequence? = null) {

    var tag: String = tag
        internal set(value) {
            if(field != value) {
                f(DEFAULT_LOG_LEVEL)
                field = value
            }
        }

    private val stringJuggler = StringJuggler(StringBuildersCache.acqireStringBuilder(), separator)

    private var logWriter: LogWriter = GLOBAL_LOGWRITER

    private var throwableToLog: Throwable? = null

    private var isDisposed = false

    inner class LogBuilder {

        var autoNewLine:Boolean = false

        var throwable: Throwable
            get() = UnsupportedOperationException()
            set(value) {
                throwableToLog = value
            }

        fun line(char: Char = '-', count: Int = 10, nlBefore: Boolean = false, nlAfter: Boolean = false) {
            if(nlBefore) nl()
            repeat(count) { a(char) }
            if(nlAfter) nl()

            if(autoNewLine) nl()
        }

        operator fun<T> KProperty0<T>.unaryPlus() {
            a(this.name).a("=").a(this())
            if(autoNewLine) nl()
        }

        operator fun Char.unaryPlus() {
            a(this)
            if(autoNewLine) nl()
        }

        operator fun String.unaryPlus() {
            a(this)
            if(autoNewLine) nl()
        }

        operator fun Any.unaryPlus() {
            a(this.toString())
            if(autoNewLine) nl()
        }

        operator fun<T> Array<T>.unaryPlus() {
            a(this)
            if(autoNewLine) nl()
        }

        operator fun ByteArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler, separator, prefix, postfix, limit, truncated)
            }
            if(autoNewLine) nl()
        }

        operator fun CharArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler, separator, prefix, postfix, limit, truncated)
            }
            if(autoNewLine) nl()
        }

        operator fun ShortArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler, separator, prefix, postfix, limit, truncated)
            }
            if(autoNewLine) nl()
        }

        operator fun IntArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler, separator, prefix, postfix, limit, truncated)
            }
            if(autoNewLine) nl()
        }

        operator fun LongArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler, separator, prefix, postfix, limit, truncated)
            }
            if(autoNewLine) nl()
        }

        operator fun FloatArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler.stringBuilder, separator, prefix, postfix, limit, truncated)
            }
            if(autoNewLine) nl()
        }

        operator fun DoubleArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler.stringBuilder, separator, prefix, postfix, limit, truncated)
            }
            if(autoNewLine) nl()
        }
    }

    private val logBuilder by lazy { LogBuilder() }

    /**
     * Disposes this log object and returns underlying stringBuilder to the pool.
     */
    fun r(logLevel: Int = DEFAULT_LOG_LEVEL) {
        f(logLevel)

        if(Loggers.mainLogger !== this) {
            StringBuildersCache.releaseStringBuilder(stringJuggler.stringBuilder)
            isDisposed = true
        }
    }

    /**
     * Shortcut for [throwable]
     * @param throwable throwable to log
     * @return this Log object
     */
    fun t(throwable: Throwable): Log {
        if (throwableToLog != null) f(DEFAULT_LOG_LEVEL)
        throwableToLog = throwable
        return this
    }

    /**
     * Special case log:<br></br>
     * [simpleclassname@hash event]<br></br>
     * For example: MainActivity@4fad3412 onCreate<br></br>
     * Convenient way to log lifecycle of object(activity,fragment, etc)
     * @param object
     * object to log
     * @param event
     * event raised by object
     * @return
     * this log object
     */
    fun event(`object`: Any, event: String): Log {
        stringJuggler.append(`object`.javaClass.simpleName)
                .append("@").append(Integer.toHexString(`object`.hashCode()))
                .append(" ").append(event)
        return this
    }

    /**
     * Appends newLine sequence to current statement.<br></br>
     * newLine sequence can be configured at startup by setting system property
     * wlog.newLine, default value is \n
     * @return
     * this object
     */
    fun nl(): Log {
        return a(NEW_LINE)
    }

    /**
     * Flushes log(if any data written or throwable was set) to the current LogWriter(typically LogCat)
     */
    private fun f(logLevel: Int) {
        val message = if (stringJuggler.isNotEmpty()) {
            stringJuggler.string
        } else {
            ""
        }
        if (message.isNotEmpty() || throwableToLog != null) {
            logWriter.write(logLevel, tag, message, throwableToLog)
            throwableToLog = null
        }
        if (stringJuggler.isNotEmpty()) {
            stringJuggler.clear()
        }
        throwableToLog = null
        logBuilder.autoNewLine = false
    }

    //region append methods

    /**
     * Appends boolean value to current log statement
     * @param value
     * boolean value to log
     * @return
     * this object
     */
    fun a(value: Boolean): Log {
        checkDisposed()
        stringJuggler.append(java.lang.Boolean.toString(value))
        return this
    }

    /**
     * Appends char value to current log statement
     * @param value
     * char value to log
     * @return
     * this object
     */
    fun a(value: Char): Log {
        checkDisposed()
        stringJuggler.append(value)
        return this
    }

    /**
     * Appends long value to current log statement
     * @param value
     * long value to log
     * @return
     * this object
     * @see .a
     */
    fun a(value: Long): Log {
        checkDisposed()
        stringJuggler.append(value.toString())
        return this
    }

    /**
     * Appends float value to current log statement
     * @param value
     * float value to log
     * @return
     * this object
     */
    fun a(value: Float): Log {
        checkDisposed()
        stringJuggler.append(value.toString())
        return this
    }

    /**
     * Appends double value to current log statement
     * @param value
     * double value to log
     * @return
     * this object
     */
    fun a(value: Double): Log {
        checkDisposed()
        stringJuggler.append(value.toString())
        return this
    }

    /**
     * Appends char[] value to current log statement
     * @param value
     * char[] value to log, can be null
     * @return
     * this object
     */
    fun a(value: CharArray?): Log {
        checkDisposed()
        stringJuggler.append(value?.let { String(it) } ?: "null")
        return this
    }

    fun a(value: Int, radix: Int = 10): Log {
        checkDisposed()
        stringJuggler.append(value.toString(radix))
        return this
    }

    /**
     * Appends `Object#toString` to current log statement
     * @param object
     * object to log, can be null
     * @return
     * this object
     */
    fun a(`object`: Any?): Log {
        checkDisposed()
        if (`object` == null) {
            stringJuggler.append("null")
            return this
        }
        if (`object`.javaClass.isArray) {
            val len = java.lang.reflect.Array.getLength(`object`)
            val objects = arrayOfNulls<Any>(len)
            for (i in 0 until len) {
                objects[i] = java.lang.reflect.Array.get(`object`, i)
            }
            return a(objects)
        }
        stringJuggler.append(`object`.toString())
        return this
    }

    fun <T> a(iterable: Iterable<T>?, format: SequenceFormat? = null): Log {
        checkDisposed()

        val f = format ?: SequenceFormat.DEFAULT

        iterable?.joinTo(stringJuggler,
                f.separator,
                f.prefix,
                f.postfix,
                f.limit,
                f.truncated)
                ?: run { stringJuggler.append("null") }

        return this
    }

    fun <T, V> a(map: Map<T, V>?): Log {
        checkDisposed()

        stringJuggler.append(map.toString())
        return this
    }

    fun a(string: String): Log {
        checkDisposed()
        stringJuggler.append(string)
        return this
    }

    fun<T> a(property: KProperty0<T>, divider: String = "="): Log {
        checkDisposed()
        stringJuggler.append(property.name).append(divider)
        a(property())
        return this
    }

    /**
     * Appends array of objects with specified SequenceFormatter
     * @param array
     * array of objects
     * @param format [SequenceFormat]
     * @param <T> any type allowed
     * @return
     * this object
     */
    fun <T> a(array: Array<T>?, format: SequenceFormat? = null): Log {
        checkDisposed()
        if (array == null) stringJuggler.append("null") else {
            val f = format ?: SequenceFormat.DEFAULT

            array.joinTo(stringJuggler,
            f.separator,
            f.prefix,
            f.postfix,
            f.limit,
            f.truncated)
        }
        return this
    }

    /**
     * Adds simplified class name to log statement, **not Object itself!**<br></br>
     * NOTE: anonymous classes should override `toString()` method which should return
     * recognizable string
     * @param object
     * object which class name will be logged
     * @return
     * this object
     */
    fun c(`object`: Any): Log {
        val klass: Class<*> = `object`.javaClass
        var className = klass.simpleName
        if (className.isEmpty()) className = klass.toString()
        if (className.indexOf('$') != -1) {
            className = `object`.toString()
            className = className.substring(className.lastIndexOf('.') + 1)
        }
        return a(className)
    }
    //endregion
    /**
     * Checks if logger is disposed(underlying StringBuilder returned to pool) and prevents
     * any manipulation if logger in this state by throwing IllegalStateException
     */
    private fun checkDisposed() {
        check(!isDisposed) { "Log $tag is disposed, usage after release() call is prohibited" }
    }

    companion object {
        //region logLevel constants
        /**
         * Priority constant for the println method; use Log.v.
         */
        const val VERBOSE = android.util.Log.VERBOSE

        /**
         * Priority constant for the println method; use Log.d.
         */
        const val DEBUG = android.util.Log.DEBUG

        /**
         * Priority constant for the println method; use Log.i.
         */
        const val INFO = android.util.Log.INFO

        /**
         * Priority constant for the println method; use Log.w.
         */
        const val WARN = android.util.Log.WARN

        /**
         * Priority constant for the println method; use Log.e.
         */
        const val ERROR = android.util.Log.ERROR

        /**
         * Priority constant for the println method.
         */
        const val ASSERT = android.util.Log.ASSERT
        //endregion

        //region shortcut methods to log simple string/throwable statements
        /**
         * Send a [.VERBOSE] log message.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         */
        fun v(tag: String, msg: String?) {
            get(tag).a(msg).r(VERBOSE)
        }

        /**
         * Send a [.VERBOSE] log message and log the exception.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         * @param tr An exception to log
         */
        fun v(tag: String, msg: String?, tr: Throwable) {
            get(tag).a(msg).t(tr).r(VERBOSE)
        }

        /**
         * Send a [.DEBUG] log message.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         */
        fun d(tag: String, msg: String?) {
            get(tag).a(msg).r(DEBUG)
        }

        /**
         * Send a [.DEBUG] log message and log the exception.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         * @param tr An exception to log
         */
        fun d(tag: String, msg: String?, tr: Throwable) {
            get(tag).a(msg).t(tr).r(DEBUG)
        }

        /**
         * Send an [.INFO] log message.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         */
        fun i(tag: String, msg: String?) {
            get(tag).a(msg).r(INFO)
        }

        /**
         * Send a [.INFO] log message and log the exception.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         * @param tr An exception to log
         */
        fun i(tag: String, msg: String?, tr: Throwable) {
            get(tag).a(msg).t(tr).r(INFO)
        }

        /**
         * Send a [.WARN] log message.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         */
        fun w(tag: String, msg: String?) {
            get(tag).a(msg).r(WARN)
        }

        /**
         * Send a [.WARN] log message and log the exception.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         * @param tr An exception to log
         */
        fun w(tag: String, msg: String?, tr: Throwable) {
            get(tag).a(msg).t(tr).r(WARN)
        }

        /**
         * Send a [.WARN] log message and log the exception.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param tr An exception to log
         */
        fun w(tag: String, tr: Throwable) {
            get(tag).t(tr).r(WARN)
        }

        /**
         * Send an [.ERROR] log message.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         */
        fun e(tag: String, msg: String?) {
            get(tag).a(msg).r(ERROR)
        }

        /**
         * Send a [.ERROR] log message and log the exception.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         * @param tr An exception to log
         */
        fun e(tag: String, msg: String?, tr: Throwable) {
            get(tag).a(msg).t(tr).r(ERROR)
        }

        /**
         * Handy function to get a loggable stack trace from a Throwable
         * @param tr An exception to log
         * @return
         * String representation of stack trace
         */
        fun getStackTraceString(tr: Throwable?): String {
            return android.util.Log.getStackTraceString(tr)
        }

        /**
         * Low-level logging call.
         * @param priority The priority/type of this log message
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         * @return The number of bytes written.
         */
        fun println(priority: Int, tag: String?, msg: String): Int {
            return android.util.Log.println(priority, tag, msg)
        }

        //endregion
        private var GLOBAL_LOGWRITER: LogWriter = LogcatWriter
        private var DEFAULT_LOG_LEVEL = DEBUG
        private var NEW_LINE = "\n"
        private var DEFAULT_SEPARATOR = " "

        /**
         * The only way to receive log object.<br></br>
         * This method is thread safe.<br></br>
         * In case of calling this method from the thread which is already has active(not released) Log object
         * then that object returned with changed tag, previous pushed data will be automatically flushed.
         * @param tag tag for log object
         * @return Log object with tag set and ready to use.
         */
        operator fun get(tag: String): Log {
            return Loggers.getLogger(tag)
        }

        operator fun invoke(tag: String,
                            separator: CharSequence? = null,
                            autoNewLine: Boolean = true,
                            logLevel: Int = DEFAULT_LOG_LEVEL,
                            block: Log.LogBuilder.() -> Unit) {
            val l = Loggers.getLogger(tag)
            l.stringJuggler.separator = separator
            l.logBuilder.autoNewLine = autoNewLine
            block(l.logBuilder)
            l.r(logLevel)
        }

        init {
            System.getProperty("wlog.logLevel")?.let {
                DEFAULT_LOG_LEVEL = when (it) {
                    "verbose" -> VERBOSE
                    "debug"   -> DEBUG
                    "info"    -> INFO
                    "error"   -> ERROR
                    else -> DEFAULT_LOG_LEVEL
                }
            }
            System.getProperty("wlog.newLine")?.let {
                NEW_LINE = it
            }

            System.getProperty("wlog.separator")?.let {
                DEFAULT_SEPARATOR = it
            }
        }
    }

}