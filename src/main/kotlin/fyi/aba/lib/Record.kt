package fyi.aba.lib

open class Record(val line: String) {
}

class DescriptiveRecord(line: String): Record(line) {
    val fields: Map<String, Field> = mapOf("record type" to buildField(::IntegerField, "record type", 0..0, line),
            "first blank field" to buildField(::BlankField, "first blank field", 1..17, line),
            "reel sequence" to buildField(::IntegerField, "reel sequence", 18..19, line),
            "financial institution" to buildField(::TextField, "financial institution", 20..22, line),
            "second blank field" to buildField(::BlankField, "second blank field", 23..29, line),
            "user name" to buildField(::LeftJustifiedField, "user name", 30..55, line),
            "ACPA number" to buildField(::IntegerField, "ACPA number", 56..61, line),
            "description" to buildField(::LeftJustifiedField, "description", 62..73, line),
            "date" to buildField(::DateField, "date", 74..79, line),
            "third blank field" to buildField(::BlankField, "third blank field", 80..119, line))
}

class DetailRecord(line: String): Record(line) {
    val fields: Map<String, Field> = mapOf("record type" to buildField(::IntegerField, "record type", 0..0, line),
            "bsb number" to buildField(::BSBField, "bsb number", 1..7, line),
            "account number" to buildField(::AccountNumberField, "account number", 8..16, line),
            "indicator" to buildField(::IndicatorField, "indicator", 17..17, line),
            "transaction code" to buildField(::TransactionCodeField, "transaction code", 18..19, line),
            "amount" to buildField(::NonZeroIntegerField, "amount", 20..29, line),
            "account name" to buildField(::LeftJustifiedField, "account name", 30..61, line),
            "lodgement reference" to buildField(::LeftJustifiedField, "lodgement reference", 62..79, line),
            "trace bsb number" to buildField(::BSBField, "trace bsb number", 80..86, line),
            "trace account number" to buildField(::AccountNumberField, "trace account number", 87..95, line),
            "remitter name" to buildField(::LeftJustifiedField, "remitter name", 96..111, line),
            "withholding tax" to buildField(::IntegerField, "withholding tax", 112..119, line))
}

class TotalRecord(line: String): Record(line) {
    val fields: Map<String, Field> = mapOf("record type" to buildField(::IntegerField, "record type", 0..0, line),
            "bsb filler" to buildField(::BSBFillerField, "bsb filler", 1..7, line),
            "first blank field" to buildField(::BlankField, "first blank field", 8..19, line),
            "net total" to buildField(::IntegerField, "net total", 20..29, line),
            "credit total" to buildField(::IntegerField, "credit total", 30..39, line),
            "debit total" to buildField(::IntegerField, "debit total", 40..49, line),
            "second blank field" to buildField(::BlankField, "second blank field", 50..73, line),
            "descriptive record count" to buildField(::IntegerField, "descriptive record count", 74..79, line),
            "third blank field" to buildField(::BlankField, "third blank field", 80..119, line))
}
