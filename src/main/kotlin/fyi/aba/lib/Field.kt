package fyi.aba.lib

import java.text.ParseException
import java.text.SimpleDateFormat

abstract class Field(val name: String, val text: String, val requiredLength: Int) {
    abstract fun errors(): List<String>

    fun lengthErrors(): List<String> {
        return when {
            requiredLength != text.length -> listOf("Expected text of length $requiredLength, but was ${text.length}")
            else -> listOf()
        }
    }

    fun isValid(): Boolean = errors().isEmpty()
}

class TextField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    override fun errors() = lengthErrors()
}

class BlankField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    override fun errors(): List<String> {
        return lengthErrors() + when {
            text.isNotEmpty() && text.any { it != ' '} -> listOf("Expected all spaces but got '$text'")
            else -> listOf()
        }
    }
}

open class IntegerField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    override fun errors(): List<String> {
        return lengthErrors() + when {
            text.isNotEmpty() && text.any { !it.isDigit() } -> listOf("Expected an integer but got '$text'")
            else -> listOf()
        }
    }
}

class NonZeroIntegerField(name: String, text: String, requiredLength: Int): IntegerField(name, text, requiredLength) {
    override fun errors(): List<String> {
        return super.errors() + when {
            text.isNotEmpty() && text.all { it in "0 " } -> listOf("Expected integer greater than 0 but got '$text'")
            else -> listOf()
        }
    }
}

class LeftJustifiedField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    override fun errors(): List<String> {
        return lengthErrors() + when {
            text.isNotEmpty() && text[0].isWhitespace() -> return listOf("Expected left justified field but first character was a space: '$text'")
            else -> listOf<String>()
        }
    }
}

class DateField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    // TODO: SimpleDateFormat is too accepting when an invalid day or month is present, need to replace it with something better
    override fun errors(): List<String> {
        val dateFormat = SimpleDateFormat("ddmmyy")
        dateFormat.isLenient = false
        return lengthErrors() +  when {
            text.any { !it.isDigit() } -> listOf("Expected date in format ddmmyy but got $text")
            else -> try {
                dateFormat.parse(text)
                listOf<String>()
            } catch (error: ParseException) {
                listOf("Expected date in format ddmmyy but got '$text'")
            }
        }
    }
}

class BSBField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    override fun errors(): List<String> {
        return lengthErrors() + when {
            text.isNotEmpty() && !listOf(
                    text[3] == '-',
                    text.filter { it != '-' }.length == 6,
                    text.filter { it != '-' }.all { it.isDigit() }
            ).all{ it } -> return listOf("Expected BSB number format (NNN-NNN) but got '$text'")
            else -> listOf<String>()
        }
    }
}

class AccountNumberField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    override fun errors(): List<String> {
        return lengthErrors() + when {
            text.isNotEmpty() && text.any { !it.isDigit() && it != '-' && it != ' ' } -> listOf("Expected only integers, spaces, and '-' but got '$text'")
            text.isNotEmpty() && text.filter { it != '-' && it != ' ' }.all { it == '0' } -> listOf("Account number cannot be all zeroes, was '$text'")
            text.isNotEmpty() && text.contains(' ') && text.trimStart().contains(' ') -> listOf("Account number must be right-justified, was '$text'")
            else -> listOf()
        }
    }
}

class IndicatorField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    override fun errors(): List<String> {
        return lengthErrors() + when {
            text.isNotEmpty() && text !in listOf(" ", "N", "W", "X", "Y") -> listOf("Expected a space or one of 'NWXY' but got '$text'")
            else -> listOf()
        }
    }
}

class TransactionCodeField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    override fun errors(): List<String> {
        return lengthErrors() + when {
            text.isNotEmpty() && text !in ((50..57) + 13).map { it.toString() } -> listOf("Expected '13' or '50' to '57' but got '$text'")
            else -> listOf()
        }
    }
}

class BSBFillerField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    override fun errors(): List<String> {
        return lengthErrors() + when {
            text != "999-999" -> listOf("Expected '999-999' but got '$text'")
            else -> listOf()
        }
    }
}

fun buildField(constructor: (String, String, Int) -> Field, name: String, range: IntRange, line: String): Field {
    if (range.first < 0 || range.last < 0 || range.first >= line.length || range.last >= line.length) {
        throw StringIndexOutOfBoundsException("Substring range $range outside of line with length ${line.length}")
    }
    return constructor(name, line.substring(range), range.count())
}
