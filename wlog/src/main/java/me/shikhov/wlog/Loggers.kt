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

import android.os.Process
import java.util.*

internal object Loggers {

    private val mutex = Any()
    private val loggers = HashMap<Int, Log>()

    val logger: Log
        get() {
            val threadId = Process.myTid()
            var logger = loggers[threadId]
            if (logger == null) {
                logger = Log("LOG")
                synchronized(mutex) {
                    loggers[threadId] = logger
                }
            }
            return logger
        }

    fun getLogger(tag: String): Log {
        val logger = logger
        logger.setTag(tag)
        return logger
    }

    fun removeLogger(log: Log) {
        synchronized(mutex) { loggers.values.remove(log) }
    }
}