import fyi.aba.lib.NonZeroIntegerField
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class TestNonZeroNonZeroIntegerField : StringSpec() {
    init {
        "correct length zero" {
            NonZeroIntegerField("test", "000", 3).errors().size shouldBe 1
        }

        "correct length non-zero" {
            NonZeroIntegerField("test", "123", 3).errors().isEmpty() shouldBe true
        }

        "incorrect length zero" {
            NonZeroIntegerField("test", "0000", 3).errors().size shouldBe 2
        }

        "incorrect length non-zero" {
            NonZeroIntegerField("test", "1234", 3).errors().size shouldBe 1
        }

        "correct length non-integer" {
            NonZeroIntegerField("test", "1ab4", 4).errors().size shouldBe 1
        }
    }
}
