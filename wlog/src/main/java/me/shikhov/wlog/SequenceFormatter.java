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

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 *  Useful class to prepare output for array
 */
public class SequenceFormatter
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

    public static final SequenceFormatter DEFAULT_FORMATTER = new SequenceFormatter();

    public static final int PREFIX = 0;
    public static final int ITEM_PREFIX = 1;
    public static final int ITEM_SUFFIX = 2;
    public static final int FIRST_ITEM_PREFIX = 3;
    public static final int LAST_ITEM_SUFFIX = 4;
    public static final int SUFFIX = 5;
    public static final int EMPTY_SEQUENCE = 6;

    private final String[] format;

    private SequenceFormatter(@NonNull Builder builder)
    {
        format = new String[DEFAULT_FORMAT.length];
        format[PREFIX] = builder.prefix;
        format[ITEM_PREFIX] = builder.itemPrefix;
        format[ITEM_SUFFIX] = builder.itemSuffix;
        format[FIRST_ITEM_PREFIX] = builder.firstItemPrefix;
        format[LAST_ITEM_SUFFIX] = builder.lastItemSuffix;
        format[SUFFIX] = builder.suffix;
        format[EMPTY_SEQUENCE] = builder.emptyArray;
    }

    private SequenceFormatter()
    {
        this.format = DEFAULT_FORMAT;
    }

    private static boolean handleNull(@NonNull StringBuilder builder,@Nullable Object object)
    {
        if(object == null)
        {
            builder.append("null");
            return true;
        }

        return false;
    }

    private static void handleEmptySequence(
            @NonNull SequenceFormatter formatter,
            @NonNull StringBuilder builder)
    {
        if(formatter.format[EMPTY_SEQUENCE] == null)
        {
            builder.append(formatter.format[PREFIX])
                    .append(formatter.format[SUFFIX]);
        }
        else
        {
            builder.append(formatter.format[EMPTY_SEQUENCE]);
        }
    }

    public <T> SequenceFormatter doFormat(@NonNull StringBuilder builder, @Nullable T[] array)
    {
        return format(this, builder, array);
    }

    public static <T> SequenceFormatter format(@NonNull StringBuilder builder, @Nullable T[] array)
    {
        return format(DEFAULT_FORMATTER, builder, array);
    }

    public static <T> SequenceFormatter format(@NonNull SequenceFormatter formatter,
                                  @NonNull StringBuilder builder, @Nullable T[] array)
    {
        if(handleNull(builder, array))
            return formatter;

        //noinspection ConstantConditions
        if(array.length == 0)
        {
            handleEmptySequence(formatter, builder);
            return formatter;
        }

        builder.append(formatter.format[PREFIX])
               .append(formatter.format[FIRST_ITEM_PREFIX])
               .append(array[0]);

        if(array.length > 1)
        {
           builder.append(formatter.format[ITEM_SUFFIX]);
        }

        for(int i = 1; i < array.length - 1; i++)
        {
            builder.append(formatter.format[ITEM_PREFIX]);
            builder.append(array[i]);
            builder.append(formatter.format[ITEM_SUFFIX]);
        }

        if(array.length > 1)
        {
            builder.append(formatter.format[ITEM_PREFIX]);
            builder.append(array[array.length - 1]);
        }

        builder.append(formatter.format[LAST_ITEM_SUFFIX])
               .append(formatter.format[SUFFIX]);

        return formatter;
    }

    public <T> SequenceFormatter doFormat(@NonNull StringBuilder builder, @Nullable Collection<T> collection)
    {
        return format(this, builder, collection);
    }

    public static <T> SequenceFormatter format(@NonNull StringBuilder builder, @Nullable Collection<T> collection)
    {
        return format(DEFAULT_FORMATTER, builder, collection);
    }

    public static <T> SequenceFormatter format(@NonNull SequenceFormatter formatter, @NonNull StringBuilder builder,
                                  @Nullable Collection<T> collection)
    {
        if(handleNull(builder, collection))
            return formatter;

        //noinspection ConstantConditions
        if(collection.isEmpty())
        {
            handleEmptySequence(formatter, builder);
            return formatter;
        }

        builder.append(formatter.format[PREFIX])
               .append(formatter.format[FIRST_ITEM_PREFIX]);

        Iterator<T> iterator = collection.iterator();

        builder.append(iterator.next());

        int count = collection.size();

        if(count > 1)
        {
            builder.append(formatter.format[ITEM_SUFFIX]);
        }

        for(int i = 1; i < count - 1; i++)
        {
            builder.append(formatter.format[ITEM_PREFIX]);
            builder.append(iterator.next());
            builder.append(formatter.format[ITEM_SUFFIX]);
        }

        if(count > 1)
        {
            builder.append(formatter.format[ITEM_PREFIX])
                   .append(iterator.next());
        }

        builder.append(formatter.format[LAST_ITEM_SUFFIX])
               .append(formatter.format[SUFFIX]);

        return formatter;
    }

    public static <T,V> SequenceFormatter format(@NonNull StringBuilder builder,
                                    @Nullable Map<T,V> map)
    {
        return format(DEFAULT_FORMATTER, builder, map);
    }

    public static <T,V> SequenceFormatter format(@NonNull SequenceFormatter formatter, @NonNull StringBuilder builder,
                                  @Nullable Map<T,V> map)
    {
        if(handleNull(builder, map))
            return formatter;

        //noinspection ConstantConditions
        if(map.isEmpty())
        {
            handleEmptySequence(formatter, builder);
            return formatter;
        }

        builder.append(formatter.format[PREFIX])
               .append(formatter.format[FIRST_ITEM_PREFIX]);

        Iterator<Map.Entry<T,V>> iterator = map.entrySet().iterator();

        Map.Entry<T,V> firstItem = iterator.next();

        handleEntry(builder, firstItem);

        int count = map.size();

        if(count > 1)
        {
            builder.append(formatter.format[ITEM_SUFFIX]);
        }

        Map.Entry<T,V> entry;

        for(int i = 1; i < count - 1; i++)
        {
            builder.append(formatter.format[ITEM_PREFIX]);

            entry = iterator.next();

            handleEntry(builder, entry);

            builder.append(formatter.format[ITEM_SUFFIX]);
        }

        if(count > 1)
        {
            entry = iterator.next();

            builder.append(formatter.format[ITEM_PREFIX]);
            handleEntry(builder, entry);
        }

        builder.append(formatter.format[LAST_ITEM_SUFFIX])
               .append(formatter.format[SUFFIX]);

        return formatter;
    }

    private static <T,V> void handleEntry(@NonNull StringBuilder builder, @NonNull Map.Entry<T,V> entry)
    {
        builder.append('{')
                .append('\'')
                .append(entry.getKey())
                .append('\'')
                .append('=')
                .append('\'')
                .append(entry.getValue())
                .append('\'')
                .append('}');
    }

    public static class Builder
    {
        private String prefix = DEFAULT_FORMAT[PREFIX];
        private String suffix = DEFAULT_FORMAT[SUFFIX];
        private String itemPrefix = DEFAULT_FORMAT[ITEM_PREFIX];
        private String itemSuffix = DEFAULT_FORMAT[ITEM_SUFFIX];
        private String firstItemPrefix = DEFAULT_FORMAT[FIRST_ITEM_PREFIX];
        private String lastItemSuffix = DEFAULT_FORMAT[LAST_ITEM_SUFFIX];
        private String emptyArray = DEFAULT_FORMAT[EMPTY_SEQUENCE];

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
