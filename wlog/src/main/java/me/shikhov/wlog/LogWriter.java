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
 * Basic interface for log writers
 * @see LogcatWriter
 * Created by Andrew on 07.05.2015.
 */
public interface LogWriter
{
    /**
     * Method should write log to underlying storage/console. <br>
     * message and throwable can't be null simultaneously.
     * @param logLevel
     *      logLevel from {@link Log#VERBOSE} to {@link Log#ASSERT}
     * @param tag
     *      nonnull tag, usually class name.
     * @param message
     *      nullable message to log
     * @param throwable
     *      nullable throwable to log
     */
    void write(int logLevel,@NonNull String tag,@Nullable String message,@Nullable Throwable throwable);
}
