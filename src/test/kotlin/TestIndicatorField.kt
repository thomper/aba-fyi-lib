import fyi.aba.lib.IndicatorField
import io.kotlintest.matchers.should
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class TestIndicatorField : StringSpec() {
    init {
        "space" {
            IndicatorField("test", " ", 1).errors().isEmpty() shouldBe true
        }

        "N" {
            IndicatorField("test", "N", 1).errors().isEmpty() shouldBe true
        }

        "W" {
            IndicatorField("test", "W", 1).errors().isEmpty() shouldBe true
        }

        "X" {
            IndicatorField("test", "X", 1).errors().isEmpty() shouldBe true
        }

        "Y" {
            IndicatorField("test", "Y", 1).errors().isEmpty() shouldBe true
        }

        "Invalid character" {
            ALL_ASCII_CHARS.filter { !" NWXY".contains(it) }.map {
                IndicatorField("test", "$it", 1).errors().size shouldBe 1
            }
        }
    }
}
