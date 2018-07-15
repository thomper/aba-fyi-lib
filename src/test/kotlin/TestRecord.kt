import io.kotlintest.specs.StringSpec
import io.kotlintest.matchers.shouldBe
import fyi.aba.lib.DescriptiveRecord
import fyi.aba.lib.DetailRecord
import fyi.aba.lib.TotalRecord

const val DESCRIPTIVE_LINE = "0                 01TST       COMPANY  TST REFUNDS      161430TST REFUND  011008                                        "
val DESCRIPTIVE_FIELD_TEXT = mapOf("record type" to "0",
        "first blank field" to "                 ",
        "reel sequence" to "01",
        "financial institution" to "TST",
        "second blank field" to "       ",
        "user name" to "COMPANY  TST REFUNDS      ",
        "ACPA number" to "161430",
        "description" to "TST REFUND  ",
        "date" to "011008",
        "third blank field" to "                                        ")

const val DETAIL_LINE = "1222-222333333333 500000011000TEST DESCRIPTION                CLOSE FEE  1111111222-222333333333TST COMPANY     00000000"
val DETAIL_FIELD_TEXT = mapOf("record type" to "1",
        "bsb number" to  "222-222",
        "account number" to  "333333333",
        "indicator" to " ",
        "transaction code" to "50",
        "amount" to "0000011000",
        "account name" to "TEST DESCRIPTION                ",
        "lodgement reference" to "CLOSE FEE  1111111",
        "trace bsb number" to "222-222",
        "trace account number" to "333333333",
        "remitter name" to "TST COMPANY     ",
        "withholding tax" to "00000000")


const val TOTAL_LINE = "7999-999            000006600000000000000000000000                        000021                                        "
val TOTAL_FIELD_TEXT = mapOf("record type" to "7",
        "bsb filler" to "999-999",
        "first blank field" to "            ",
        "net total" to "0000066000",
        "credit total" to "0000000000",
        "debit total" to "0000000000",
        "second blank field" to "                        ",
        "descriptive record count" to "000021",
        "third blank field" to "                                        ")

class TestRecord : StringSpec() {
    init {
        "field split descriptive record" {
            val fields = DescriptiveRecord(DESCRIPTIVE_LINE).fields
            fields.size shouldBe DESCRIPTIVE_FIELD_TEXT.size
            fields.keys.map { key -> fields[key]!!.text shouldBe DESCRIPTIVE_FIELD_TEXT[key] }
        }

        "field split detail record" {
            val fields = DetailRecord(DETAIL_LINE).fields
            fields.size shouldBe DETAIL_FIELD_TEXT.size
            fields.keys.map { key -> fields[key]!!.text shouldBe DETAIL_FIELD_TEXT[key] }
        }

        "field split total record" {
            val fields = TotalRecord(TOTAL_LINE).fields
            fields.size shouldBe TOTAL_FIELD_TEXT.size
            fields.keys.map { key -> fields[key]!!.text shouldBe TOTAL_FIELD_TEXT[key] }
        }
    }
}


