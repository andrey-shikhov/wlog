/*******************************************************************************
 * Copyright 2015-2021 Andrew Shikhov.
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

import androidx.annotation.CheckResult
import kotlin.reflect.KProperty0

/**
 * Main entry for this library.
 * "Without loss of generality"
 * @see .get
 * @see .r
 * @see .append
 */
sealed class Log(open val tag: String) {

    protected abstract fun onRelease()

    internal abstract val stringJuggler: StringJuggler

    private var logWriter: LogWriter = GLOBAL_LOGWRITER

    protected var throwableToLog: Throwable? = null
        private set

    inner class LogBuilder(internal val suffixAction: Log.() -> Unit) {

        var throwable: Throwable? by ::throwableToLog

        fun line(char: Char = '-', count: Int = 10, nlBefore: Boolean = false, nlAfter: Boolean = false) {
            if (nlBefore) nl()
            repeat(count) { a(char) }
            if (nlAfter) nl()

            suffixAction()
        }

        operator fun <T> KProperty0<T>.unaryPlus() {
            a(this.name).a("=").a(this())

            suffixAction()
        }

        operator fun Char.unaryPlus() {
            a(this)

            suffixAction()
        }

        operator fun String.unaryPlus() {
            a(this)

            suffixAction()
        }

        operator fun Any.unaryPlus() {
            a(this.toString())

            suffixAction()
        }

        operator fun <T> Array<T>.unaryPlus() {
            a(this.contentToString())

            suffixAction()
        }

        operator fun ByteArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler, separator, prefix, postfix, limit, truncated)
            }

            suffixAction()
        }

        operator fun CharArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler, separator, prefix, postfix, limit, truncated)
            }
            suffixAction()
        }

        operator fun ShortArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler, separator, prefix, postfix, limit, truncated)
            }

            suffixAction()
        }

        operator fun IntArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler, separator, prefix, postfix, limit, truncated)
            }

            suffixAction()
        }

        operator fun LongArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler, separator, prefix, postfix, limit, truncated)
            }
            suffixAction()
        }

        operator fun FloatArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler, separator, prefix, postfix, limit, truncated)
            }

            suffixAction()
        }

        operator fun DoubleArray.unaryPlus() {
            val arr = this
            with(SequenceFormat.DEFAULT) {
                arr.joinTo(stringJuggler, separator, prefix, postfix, limit, truncated)
            }

            suffixAction()
        }

        operator fun String.rangeTo(param: Any) {
            a(this).a("=").a(param)
        }
    }

    /**
     * Disposes this log object and returns underlying stringBuilder to the pool.
     */
    fun r(logLevel: Int = DEFAULT_LOG_LEVEL) {
        f(logLevel)
        onRelease()
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
    @CheckResult
    fun event(`object`: Any, event: String, additionalInfo: String = ""): Log {
        stringJuggler.append(`object`.javaClass.simpleName)
            .append('@').append(`object`.hashCode().toString(16))
            .append(' ').append(event).append(' ').append(additionalInfo)
        return this
    }

    /**
     * Appends newLine sequence to current statement.<br></br>
     * newLine sequence can be configured at startup by setting system property
     * wlog.newLine, default value is \n
     * @return
     * this object
     */
    fun nl() = a(NEW_LINE)

    /**
     * Flushes log(if any data written or throwable was set) to the current LogWriter(typically LogCat)
     */
    private fun f(logLevel: Int) {
        val message = stringJuggler.toString()

        if (message.isNotEmpty() || throwableToLog != null) {
            logWriter.write(logLevel, tag, message, throwableToLog)
            throwableToLog = null
        }
        if (stringJuggler.isNotEmpty()) {
            stringJuggler.clear()
        }
        throwableToLog = null
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
        stringJuggler.append(value?.let { String(it) } ?: "null")
        return this
    }

    fun a(value: Int, radix: Int = 10): Log {
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
        val f = format ?: SequenceFormat.DEFAULT

        iterable?.joinTo(
            stringJuggler,
            f.separator,
            f.prefix,
            f.postfix,
            f.limit,
            f.truncated
        )
            ?: run { stringJuggler.append("null") }

        return this
    }

    fun <T, V> a(map: Map<T, V>?): Log {
        stringJuggler.append(map.toString())
        return this
    }

    fun a(string: String): Log {
        stringJuggler.append(string)
        return this
    }

    fun <T> a(property: KProperty0<T>, divider: String = "="): Log {
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
    fun <T> a(array: Array<T>?, format: SequenceFormat = SequenceFormat.DEFAULT): Log {
        if (array == null) stringJuggler.append("null") else {
            array.joinTo(
                stringJuggler,
                format.separator,
                format.prefix,
                format.postfix,
                format.limit,
                format.truncated
            )
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
            invoke(tag).a(msg).r(VERBOSE)
        }

        /**
         * Send a [.VERBOSE] log message and log the exception.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         * @param tr An exception to log
         */
        fun v(tag: String, msg: String?, tr: Throwable) {
            invoke(tag).a(msg).t(tr).r(VERBOSE)
        }

        /**
         * Send a [.DEBUG] log message.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         */
        fun d(tag: String, msg: String?) {
            invoke(tag).a(msg).r(DEBUG)
        }

        /**
         * Send a [.DEBUG] log message and log the exception.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         * @param tr An exception to log
         */
        fun d(tag: String, msg: String?, tr: Throwable) {
            invoke(tag).a(msg).t(tr).r(DEBUG)
        }

        /**
         * Send an [.INFO] log message.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         */
        fun i(tag: String, msg: String?) {
            invoke(tag).a(msg).r(INFO)
        }

        /**
         * Send a [.INFO] log message and log the exception.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         * @param tr An exception to log
         */
        fun i(tag: String, msg: String?, tr: Throwable) {
            invoke(tag).a(msg).t(tr).r(INFO)
        }

        /**
         * Send a [.WARN] log message.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         */
        fun w(tag: String, msg: String?) {
            invoke(tag).a(msg).r(WARN)
        }

        /**
         * Send a [.WARN] log message and log the exception.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         * @param tr An exception to log
         */
        fun w(tag: String, msg: String?, tr: Throwable) {
            invoke(tag).a(msg).t(tr).r(WARN)
        }

        /**
         * Send a [.WARN] log message and log the exception.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param tr An exception to log
         */
        fun w(tag: String, tr: Throwable) {
            invoke(tag).t(tr).r(WARN)
        }

        /**
         * Send an [.ERROR] log message.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         */
        fun e(tag: String, msg: String?) {
            invoke(tag).a(msg).r(ERROR)
        }

        /**
         * Send a [.ERROR] log message and log the exception.
         * @param tag Used to identify the source of a log message.  It usually identifies
         * the class or activity where the log call occurs.
         * @param msg The message you would like logged.
         * @param tr An exception to log
         */
        fun e(tag: String, msg: String?, tr: Throwable) {
            invoke(tag).a(msg).t(tr).r(ERROR)
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
        internal const val DEFAULT_SEPARATOR = ""

        /**
         * The only way to receive log object.<br></br>
         * This method is thread safe.<br></br>
         * In case of calling this method from the thread which is already has active(not released) Log object
         * then that object returned with changed tag, previous pushed data will be automatically flushed.
         * @param tag tag for log object
         * @return Log object with tag set and ready to use.
         */
        @Deprecated("To unify api, use Log() function", ReplaceWith("invoke(tag)", ""))
        @CheckResult
        operator fun get(tag: String): Log {
            return Loggers.getLogger(tag)
        }

        @CheckResult
        operator fun invoke(tag: String): Log {
            return Loggers.getLogger(tag)
        }

        operator fun invoke(
            tag: String,
            separator: CharSequence = DEFAULT_SEPARATOR,
            autoNewLine: Boolean = true,
            logLevel: Int = DEFAULT_LOG_LEVEL,
            block: Log.LogBuilder.() -> Unit
        ) {
            val l = Loggers.getLogger(tag, separator)
            val builder = if(autoNewLine) l.LogBuilder { nl() } else l.LogBuilder {  }
            block(builder)
            l.r(logLevel)
        }

        init {
            System.getProperty("wlog.logLevel")?.let {
                DEFAULT_LOG_LEVEL = when (it) {
                    "verbose" -> VERBOSE
                    "debug" -> DEBUG
                    "info" -> INFO
                    "error" -> ERROR
                    else -> DEFAULT_LOG_LEVEL
                }
            }
            System.getProperty("wlog.newLine")?.let {
                NEW_LINE = it
            }
        }
    }
}

internal class MainLog : Log("") {

    override val stringJuggler = MainLogStringJuggler

    private fun isEmpty(): Boolean = !stringJuggler.isNotEmpty() && throwableToLog == null

    override var tag: String = ""
        private set

    var separator: CharSequence? = DEFAULT_SEPARATOR
        private set

    fun setup(tag: String, separator: CharSequence): MainLog {
        check(isEmpty())

        this.tag = tag
        stringJuggler.setSeparator(separator)
        return this
    }

    override fun onRelease() {
        stringJuggler.clear()
    }
}

internal class SingleStatementLog(tag: String, separator: CharSequence? = null) : Log(tag) {

    override var stringJuggler: StringJuggler = StringJuggler(separator)

    override fun onRelease() {
        stringJuggler = stringJuggler.dispose()
    }
}