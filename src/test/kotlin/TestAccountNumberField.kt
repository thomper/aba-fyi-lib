import fyi.aba.lib.AccountNumberField
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class TestAccountNumberField : StringSpec() {
    init {
        "valid account number with hyphen" {
            AccountNumberField("test", "0123-4567", 9).errors().isEmpty() shouldBe true
        }

        "valid account number without hyphen" {
            AccountNumberField("test", "012345678", 9).errors().isEmpty() shouldBe true
        }

        "contains non-integers other than hyphen" {
            AccountNumberField("test", "0123-456a", 9).errors().size shouldBe 1
        }

        "not right-justified" {
            AccountNumberField("test", "0123-456 ", 9).errors().size shouldBe 1
        }

        "right-justified" {
            AccountNumberField("test", " 0123-456", 9).errors().isEmpty() shouldBe true
        }
    }
}
