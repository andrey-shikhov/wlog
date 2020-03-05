package me.shikhov.wlog

internal class StringJuggler(val stringBuilder: StringBuilder,
                             var separator: CharSequence?) : Appendable {

    fun isNotEmpty() = stringBuilder.isNotEmpty()

    val string: String
        get() = stringBuilder.toString()

    fun clear() {
        stringBuilder.clear()
    }

    override fun append(csq: CharSequence?): java.lang.Appendable {
        stringBuilder.append(csq)
        separator?.let { stringBuilder.append(it) }
        return this
    }

    override fun append(csq: CharSequence?, start: Int, end: Int): java.lang.Appendable {
        stringBuilder.append(csq, start, end)
        separator?.let { stringBuilder.append(it) }
        return this
    }

    override fun append(c: Char): java.lang.Appendable {
        stringBuilder.append(c)
        separator?.let { stringBuilder.append(it) }
        return this
    }
}