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

import androidx.annotation.CheckResult

/**
 * Useful class to prepare output for array
 */
class SequenceFormat(val prefix: CharSequence,
                     val postfix: CharSequence,
                     val separator: CharSequence,
                     val limit: Int,
                     val truncated: CharSequence) {

    /**
     * Builder function which allows modify this instance without mandatory filling of all fields
     * @return new instance of SequenceFormatter
     */
    @CheckResult
    fun new(prefix: CharSequence? = null,
            postfix: CharSequence? = null,
            separator: CharSequence? = null,
            limit: Int? = null,
            truncated: CharSequence? = null): SequenceFormat {

        return SequenceFormat(
                prefix ?: this.prefix,
                postfix ?: this.postfix,
                separator ?: this.separator,
                limit ?: this.limit,
                truncated ?: this.truncated)
    }

    companion object {
        val DEFAULT = SequenceFormat("[", "]", ", ", -1, "...")
    }
}