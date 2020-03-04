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

/**
 * Wrapper for android Log class, controls length of the message(4k limit)
 */
class LogcatWriter private constructor() : LogWriter {
    override fun write(logLevel: Int, tag: String, message: String?, throwable: Throwable?) {
        if (throwable == null) {
            if(message != null)
                write(logLevel, tag, message)
        } else {
            android.util.Log.println(logLevel, tag, "$message\n${Log.getStackTraceString(throwable)}")
        }
    }

    private fun write(logLevel: Int, tag: String, message: String) {
        if (message.length > 4000) {
            val count = message.length / 4000
            for (i in 0 until count) {
                android.util.Log.println(logLevel, tag, message.substring(i * 4000, (i + 1) * 4000))
            }
            android.util.Log.println(logLevel, tag, message.substring(count * 4000))
        } else {
            android.util.Log.println(logLevel, tag, message)
        }
    }

    companion object {
        @JvmStatic
        val instance = LogcatWriter()
    }
}