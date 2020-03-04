package me.shikhov.wlog;


import androidx.annotation.NonNull;

class StringJuggler
{
    protected final StringBuilder stringBuilder;

    public StringJuggler(@NonNull StringBuilder stringBuilder)
    {
        this.stringBuilder = stringBuilder;
    }

    public boolean hasData()
    {
        return stringBuilder.length() > 0;
    }

    public String getString()
    {
        return stringBuilder.toString();
    }

    public void clear()
    {
        stringBuilder.delete(0, stringBuilder.length());
    }

    @NonNull
    public StringBuilder getStringBuilder()
    {
        return stringBuilder;
    }

    public StringJuggler append(String text)
    {
        stringBuilder.append(text);

        return this;
    }
}
