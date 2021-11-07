package me.shikhov.wlog

import androidx.annotation.CheckResult

/**
 * simply throws [IllegalStateException], more explicit way to show that
 * some api must not be called but due to obligations they must have be implemented
 */
@Suppress("FunctionName")
internal fun INVALID(): Nothing = throw IllegalStateException()

/**
 * Interface to hide underlying StringBuilder implementation
 * For now 3 implementation available:
 * 1. [SimpleStringJuggler] proxy to StringBuilder
 * 2. [SimpleStringJugglerWithSeparator] extends [SimpleStringJuggler],
 * adds separator after each operation on Appendable, which was set during creation of the StringJuggler
 * 3. [DisposedStringJuggler] special implementation stub to use to ensure that no meaningful operation will be lost
 * because [Log] instance was disposed(StringBuilder returned to the pull)
 */
internal sealed interface StringJuggler : Appendable {

    fun clear()

    fun isNotEmpty(): Boolean

    @CheckResult
    fun dispose(): StringJuggler
}

@CheckResult
internal fun StringJuggler(separator: CharSequence?): StringJuggler {
    return separator?.takeIf { it.isNotEmpty() }?.run {
        SimpleStringJugglerWithSeparator(StringBuildersCache.acqireStringBuilder(), this)
    } ?: SimpleStringJuggler(StringBuildersCache.acqireStringBuilder())
}

/**
 * Special StringJuggler to replace disposed StringJuggler to ensure all the future actions
 * with Log instance will fail as quick as possible
 */
private object DisposedStringJuggler : StringJuggler {

    override fun dispose() = this

    override fun clear() = INVALID()

    override fun isNotEmpty() = INVALID()

    override fun append(csq: CharSequence?) = INVALID()

    override fun append(csq: CharSequence?, start: Int, end: Int) = INVALID()

    override fun append(c: Char) = INVALID()

    override fun toString() = INVALID()
}

/**
 * Proxy to [StringBuilder] object
 */
internal open class SimpleStringJuggler(val stringBuilder: StringBuilder) : StringJuggler {

    final override fun clear() {
        stringBuilder.clear()
    }

    final override fun isNotEmpty() = stringBuilder.isNotEmpty()

    override fun append(csq: CharSequence?) : Appendable {
        stringBuilder.append(csq)
        return this
    }

    override fun append(csq: CharSequence?, start: Int, end: Int) : java.lang.Appendable {
        stringBuilder.append(csq, start, end)
        return this
    }

    override fun append(c: Char): java.lang.Appendable {
        stringBuilder.append(c)
        return this
    }

    override fun dispose(): StringJuggler = DisposedStringJuggler

    override fun toString() = stringBuilder.toString()
}

internal class SimpleStringJugglerWithSeparator(stringBuilder: StringBuilder,
                                               var separator: CharSequence) : SimpleStringJuggler(stringBuilder) {

    override fun append(csq: CharSequence?): Appendable {
        super.append(csq)
        stringBuilder.append(separator)
        return this
    }

    override fun append(csq: CharSequence?, start: Int, end: Int): Appendable {
        super.append(csq, start, end)
        stringBuilder.append(separator)
        return this
    }

    override fun append(c: Char): Appendable {
        super.append(c)
        stringBuilder.append(separator)
        return this
    }
}

internal object MainLogStringJuggler : StringJuggler {

    // string builder will be shared between two strategies
    private val stringBuilder = StringBuilder(1024)

    private val separatorJuggler = SimpleStringJugglerWithSeparator(stringBuilder, "")
    private val simpleJuggler = SimpleStringJuggler(stringBuilder)

    private var current: StringJuggler = simpleJuggler

    fun setSeparator(separator: CharSequence?) {
        current = if(separator != null && separator.isNotEmpty())
            separatorJuggler.apply { this.separator = separator }
        else
            simpleJuggler
    }

    override fun clear() { stringBuilder.clear() }

    override fun isNotEmpty() = stringBuilder.isNotEmpty()

    override fun dispose() = INVALID()

    override fun append(csq: CharSequence?): Appendable {
        current.append(csq)
        return this
    }

    override fun append(csq: CharSequence?, start: Int, end: Int): Appendable {
        current.append(csq, start, end)
        return this
    }

    override fun append(c: Char): Appendable {
        current.append(c)
        return this
    }

    override fun toString() = current.toString()
}