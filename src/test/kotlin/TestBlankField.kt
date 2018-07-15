import fyi.aba.lib.BlankField
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class TestBlankField : StringSpec() {
    init {
        "not blank" {
            BlankField("test", "aaaa", 4).errors().size shouldBe 1
        }

        "blank" {
            BlankField("test", "    ", 4).errors().isEmpty() shouldBe true
        }

        "too long" {
            BlankField("test", "        ", 4).errors().size shouldBe 1
        }

        "too short" {
            BlankField("test", "  ", 4).errors().size shouldBe 1
        }
    }
}
