package me.shikhov.wlog;


import androidx.annotation.NonNull;

class SpacerStringJuggler extends StringJuggler
{
    private final String spacer;

    public SpacerStringJuggler(@NonNull StringBuilder stringBuilder, @NonNull String spacer)
    {
        super(stringBuilder);

        this.spacer = spacer;
    }

    @Override
    public StringJuggler append(String text)
    {
        super.append(text);
        super.append(spacer);

        return this;
    }
}
