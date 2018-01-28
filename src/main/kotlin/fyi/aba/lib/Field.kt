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

class IntegerField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    override fun errors(): List<String> {
        return lengthErrors() + when {
            text.isNotEmpty() && text.any { !it.isDigit() } -> listOf("Expected an integer but got '$text'")
            else -> listOf()
        }
    }
}

class NonZeroIntegerField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    override fun errors(): List<String> {
        return lengthErrors() + when {
            text.isNotEmpty() && text.any { !it.isDigit() } -> listOf("Expected an integer but got '$text'")
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
    override fun errors(): List<String> {
        val dateFormat = SimpleDateFormat("DDmmyy")
        dateFormat.isLenient = false
        return lengthErrors() + try {
            dateFormat.parse(text)
            listOf<String>()
        } catch (error: ParseException) {
            listOf("Expected date informat DDMMYY but got '$text'")
        }
    }
}

class BSBField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    override fun errors(): List<String> {
        return lengthErrors() + when {
            text.isNotEmpty() &&
                    (text[3] != '-' ||
                     text.filter { it != '-' }.length != 6 ||
                     text.filter { it != '-' }.map { !it.isDigit() }.any()) -> return listOf("Expected BSB number format (NNN-NNN) but got '$text'")
            else -> listOf<String>()
        }
    }
}

class AccountNumberField(name: String, text: String, requiredLength: Int): Field(name, text, requiredLength) {
    override fun errors(): List<String> {
        return lengthErrors() + when {
            text.isNotEmpty() && text.any { !it.isDigit() && it != '-' } -> listOf("Expected only numbers and '-' but got '$text'")
            text.isNotEmpty() && text.filter { it != '-' }.all { it == '0' } -> listOf("Account number cannot be all zeroes, was '$text'")
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
        throw StringIndexOutOfBoundsException("Line had length ${line.length} but substring range was $range")
    }
    return constructor(name, line.substring(range), range.count())
}