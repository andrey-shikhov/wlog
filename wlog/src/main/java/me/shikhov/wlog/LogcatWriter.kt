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

import java.io.PrintWriter
import java.io.StringWriter


/**
 * Wrapper for android Log class, controls length of the message(4k limit)
 */
object LogcatWriter : LogWriter {

    override fun write(logLevel: Int, tag: String, message: String?, throwable: Throwable?) {
        if(message != null) {
            if(throwable != null) {
                write(logLevel, tag, buildMessage(message, throwable))
            } else {
                write(logLevel, tag, message)
            }
        } else {
            if(throwable != null) {
                write(logLevel, tag, android.util.Log.getStackTraceString(throwable))
            }
        }
    }

    private fun write(logLevel: Int, tag: String, message: String) {
        val len = message.utf8Length()
        if (len > 4000) {
            message.split(4000).forEach {
                android.util.Log.println(logLevel, tag, it)
            }
        } else {
            android.util.Log.println(logLevel, tag, message)
        }
    }

    private fun buildMessage(message: String, throwable: Throwable) : String {
        val sw = StringWriter()
        with(PrintWriter(sw)) {
            println(message)
            println()
            throwable.printStackTrace(this)
            flush()
        }
        return sw.toString()
    }
}

internal fun String.split(chunkByteSize: Int): List<String> {

    val chunks = mutableListOf<String>()

    var count = 0
    var i = 0
    val len = this.length
    var chunkStart = 0

    while (i < len) {
        val ch = this[i]
        when {
            ch.toInt() <= 0x7F -> count++
            ch.toInt() <= 0x7FF -> count += 2
            Character.isHighSurrogate(ch) -> {
                count += 4
                ++i
            }
            else -> count += 3
        }

        i++

        if(count >= chunkByteSize) {
            chunks += substring(chunkStart, i)
            chunkStart = i
            count = 0
        }
    }

    if(count > 0) {
        chunks += substring(chunkStart)
    }

    return chunks
}

internal fun CharSequence.utf8Length(): Int {
    var count = 0
    var i = 0
    val len = this.length
    while (i < len) {
        val ch = this[i]
        when {
            ch.toInt() <= 0x7F -> count++
            ch.toInt() <= 0x7FF -> count += 2
            Character.isHighSurrogate(ch) -> {
                count += 4
                ++i
            }
            else -> count += 3
        }
        i++
    }
    return count
}