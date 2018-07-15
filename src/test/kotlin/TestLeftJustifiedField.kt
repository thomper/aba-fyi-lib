import fyi.aba.lib.LeftJustifiedField
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class TestLeftJustifiedField : StringSpec() {
    init {
        "left justified one space" {
            LeftJustifiedField("test", "abcd ", 5).errors().isEmpty() shouldBe true
        }

        "left justified many spaces" {
            LeftJustifiedField("test", "abcd   ", 7).errors().isEmpty() shouldBe true
        }

        "blank" {
            LeftJustifiedField("test", "    ", 4).errors().size shouldBe 1
        }

        "right justified many spaces" {
            LeftJustifiedField("test", "   abcd", 7).errors().size shouldBe 1
        }

        "right justified one space" {
            LeftJustifiedField("test", " abcd", 5).errors().size shouldBe 1
        }
    }
}
