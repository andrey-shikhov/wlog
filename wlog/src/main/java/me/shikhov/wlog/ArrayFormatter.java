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
 *  Useful class to prepare output for array
 */
public class ArrayFormatter
{
    private static final String[] DEFAULT_FORMAT =
            {
              "[",
              " ",
              ",",
              "",
              "",
              "]",
              null
            };

    private static final ArrayFormatter DEFAULT_FORMATTER = new ArrayFormatter();

    @NonNull
    public static ArrayFormatter getDefaultFormatter()
    {
        return DEFAULT_FORMATTER;
    }

    public static final int PREFIX = 0;
    public static final int ITEM_PREFIX = 1;
    public static final int ITEM_SUFFIX = 2;
    public static final int FIRST_ITEM_PREFIX = 3;
    public static final int LAST_ITEM_SUFFIX = 4;
    public static final int SUFFIX = 5;
    public static final int EMPTY_ARRAY = 6;

    private final String[] format;

    private ArrayFormatter(@NonNull Builder builder)
    {
        format = new String[DEFAULT_FORMAT.length];
        format[PREFIX] = builder.prefix;
        format[ITEM_PREFIX] = builder.itemPrefix;
        format[ITEM_SUFFIX] = builder.itemSuffix;
        format[FIRST_ITEM_PREFIX] = builder.firstItemPrefix;
        format[LAST_ITEM_SUFFIX] = builder.lastItemSuffix;
        format[SUFFIX] = builder.suffix;
        format[EMPTY_ARRAY] = builder.emptyArray;
    }

    private ArrayFormatter()
    {
        this.format = DEFAULT_FORMAT;
    }


    public static <T> void format(@NonNull ArrayFormatter formatter, @NonNull StringBuilder builder, @Nullable T[] array)
    {
        if(array == null)
        {
            builder.append("null");
            return;
        }

        if(array.length == 0)
        {
            if(formatter.format[EMPTY_ARRAY] == null)
            {
                builder.append(formatter.format[PREFIX])
                        .append(formatter.format[SUFFIX]);
            }
            else
            {
                builder.append(formatter.format[EMPTY_ARRAY]);
            }

            return;
        }

        builder.append(formatter.format[PREFIX]);

        builder.append(formatter.format[FIRST_ITEM_PREFIX]);

        builder.append(array[0]);

        for(int i = 1; i < array.length - 1; i++)
        {
            builder.append(formatter.format[ITEM_PREFIX]);
            builder.append(array[i]);
            builder.append(formatter.format[ITEM_SUFFIX]);
        }

        if(array.length > 1)
        {
            builder.append(array[array.length - 1]);
        }

        builder.append(formatter.format[LAST_ITEM_SUFFIX]);

        builder.append(formatter.format[SUFFIX]);
    }

    public static class Builder
    {
        private String prefix = DEFAULT_FORMAT[PREFIX];
        private String suffix = DEFAULT_FORMAT[SUFFIX];
        private String itemPrefix = DEFAULT_FORMAT[ITEM_PREFIX];
        private String itemSuffix = DEFAULT_FORMAT[ITEM_SUFFIX];
        private String firstItemPrefix = DEFAULT_FORMAT[FIRST_ITEM_PREFIX];
        private String lastItemSuffix = DEFAULT_FORMAT[LAST_ITEM_SUFFIX];
        private String emptyArray = DEFAULT_FORMAT[EMPTY_ARRAY];

        private Builder()
        {

        }

        @NonNull
        public static Builder arrayFormatter()
        {
            return new Builder();
        }

        @NonNull
        public Builder setPrefix(@NonNull String prefix)
        {
            this.prefix = prefix;
            return this;
        }

        @NonNull
        public Builder setSuffix(@NonNull String suffix)
        {
            this.suffix = suffix;
            return this;
        }

        @NonNull
        public Builder setItemPrefix(@NonNull String itemPrefix)
        {
            this.itemPrefix = itemPrefix;
            return this;
        }

        @NonNull
        public Builder setItemSuffix(@NonNull String itemSuffix)
        {
            this.itemSuffix = itemSuffix;
            return this;
        }

        @NonNull
        public Builder setFirstItemPreffix(@NonNull String firstItemPreffix)
        {
            this.firstItemPrefix = firstItemPreffix;
            return this;
        }

        @NonNull
        public Builder setLastItemSuffix(@NonNull String lastItemSuffix)
        {
            this.lastItemSuffix = lastItemSuffix;
            return this;
        }

        @NonNull
        public Builder setEmptyArray(@Nullable String emptyArray)
        {
            this.emptyArray = emptyArray;
            return this;
        }
    }
}
