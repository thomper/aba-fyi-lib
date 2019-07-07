package fyi.aba.lib

abstract class Record(val line: String) {
    abstract val fields: Map<String, Field>


}

class DescriptiveRecord(line: String): Record(line) {
    override val fields: Map<String, Field> = listOf(buildField("record type", ::IntegerField, 0..0, line),
            buildField("first blank field", ::BlankField, 1..17, line),
            buildField("reel sequence", ::IntegerField, 18..19, line),
            buildField("financial institution", ::TextField, 20..22, line),
            buildField("second blank field", ::BlankField, 23..29, line),
            buildField("user name", ::LeftJustifiedField, 30..55, line),
            buildField("ACPA number", ::IntegerField, 56..61, line),
            buildField("description", ::LeftJustifiedField, 62..73, line),
            buildField("date", ::DateField, 74..79, line),
            buildField("third blank field", ::BlankField, 80..119, line))
            .associateBy { it.name }
}

class DetailRecord(line: String): Record(line) {
    override val fields: Map<String, Field> = listOf(buildField("record type", ::IntegerField, 0..0, line),
            buildField("bsb number", ::BSBField, 1..7, line),
            buildField("account number", ::AccountNumberField, 8..16, line),
            buildField("indicator", ::IndicatorField, 17..17, line),
            buildField("transaction code", ::TransactionCodeField, 18..19, line),
            buildField("amount", ::NonZeroIntegerField, 20..29, line),
            buildField("account name", ::LeftJustifiedField, 30..61, line),
            buildField("lodgement reference", ::LeftJustifiedField, 62..79, line),
            buildField("trace bsb number", ::BSBField, 80..86, line),
            buildField("trace account number", ::AccountNumberField, 87..95, line),
            buildField("remitter name", ::LeftJustifiedField, 96..111, line),
            buildField("withholding tax", ::IntegerField, 112..119, line))
            .associateBy { it.name }
}

class TotalRecord(line: String): Record(line) {
    override val fields: Map<String, Field> = listOf(buildField("record type", ::IntegerField, 0..0, line),
            buildField("bsb filler", ::BSBFillerField, 1..7, line),
            buildField("first blank field", ::BlankField, 8..19, line),
            buildField("net total", ::IntegerField, 20..29, line),
            buildField("credit total", ::IntegerField, 30..39, line),
            buildField("debit total", ::IntegerField, 40..49, line),
            buildField("second blank field", ::BlankField, 50..73, line),
            buildField("descriptive record count", ::IntegerField, 74..79, line),
            buildField("third blank field", ::BlankField, 80..119, line))
            .associateBy { it.name }
}
