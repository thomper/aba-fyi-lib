import fyi.aba.lib.TransactionCodeField
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

val ALLOWED_VALUES = ((50..57) + 13).map { it.toString() }
val PROHIBITED_VALUES = ALL_ASCII_CHAR_PAIRS.filter { !ALLOWED_VALUES.contains(it) }

class TestTransactionCodeField : StringSpec() {
    init {
        "valid" {
            ALLOWED_VALUES.map {
                TransactionCodeField("test", it, 2).errors().isEmpty() shouldBe true
            }
        }

        "invalid" {
            PROHIBITED_VALUES.map {
                TransactionCodeField("test", it, 2).errors().size shouldBe 1
            }
        }
    }
}
