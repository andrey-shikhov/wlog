package me.shikhov.wlog;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 *
 * Created by Andrew on 07.06.2015.
 */
public class ArrayFormatter
{
    private static final String[] DEFAULT_FORMAT =
            {
              "[",
              " ",
              ",",
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
    public static final int LAST_ITEM_SUFFIX = 3;
    public static final int SUFFIX = 4;
    public static final int EMPTY_ARRAY = 5;

    private final String[] format;

    private ArrayFormatter(@NonNull Builder builder)
    {
        format = new String[6];
        format[PREFIX] = builder.prefix;
        format[ITEM_PREFIX] = builder.itemPrefix;
        format[ITEM_SUFFIX] = builder.itemSuffix;
        format[LAST_ITEM_SUFFIX] = builder.lastItemSuffix;
        format[SUFFIX] = builder.suffix;
        format[EMPTY_ARRAY] = builder.emptyArray;
    }

    private ArrayFormatter()
    {
        this.format = DEFAULT_FORMAT;
    }

    public static void format(@NonNull ArrayFormatter formatter, @NonNull StringBuilder builder, @Nullable Object[] array)
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

        for(int i = 0; i < array.length - 1; i++)
        {
            builder.append(formatter.format[ITEM_PREFIX]);
            builder.append(array[i]);
            builder.append(formatter.format[ITEM_SUFFIX]);
        }

        builder.append(formatter.format[ITEM_SUFFIX]);
        builder.append(array[array.length - 1]);
        builder.append(formatter.format[LAST_ITEM_SUFFIX]);

        builder.append(formatter.format[SUFFIX]);
    }

    public static class Builder
    {
        private String prefix = "[";
        private String suffix = "]";
        private String itemPrefix = "";
        private String itemSuffix = "";
        private String lastItemSuffix = "";
        private String emptyArray = null;

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
