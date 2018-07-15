import fyi.aba.lib.IntegerField
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class TestIntegerField : StringSpec() {
    init {
        "correct length integer" {
            IntegerField("test", "123", 3).errors().isEmpty() shouldBe true
        }

        "correct length non-integer" {
            IntegerField("test", "abc", 3).errors().size shouldBe 1
        }

        "incorrect length integer" {
            IntegerField("test", "1234", 3).errors().size shouldBe 1
        }

        "incorrect length non-integer" {
            IntegerField("test", "abcd", 3).errors().size shouldBe 2
        }

        "correct length mixed" {
            IntegerField("test", "1ab2", 4).errors().size shouldBe 1
        }
    }
}
