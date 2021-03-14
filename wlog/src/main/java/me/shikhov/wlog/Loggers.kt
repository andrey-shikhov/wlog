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

import android.os.Looper

internal object Loggers {

    private val mainThreadId = Looper.getMainLooper().thread.id

    internal val mainLogger: Log by lazy {
        Log("LOG")
    }

    fun getLogger(tag: String): Log {
        val threadId = Thread.currentThread().id

        if(threadId == mainThreadId) {
            return mainLogger.apply {
                this.tag = tag
            }
        }

        return Log(tag)
    }
}