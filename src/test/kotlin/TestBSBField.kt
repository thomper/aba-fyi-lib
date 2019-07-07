import fyi.aba.lib.BSBField
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class TestBSBField : StringSpec() {
    init {
        "valid BSB" {
            BSBField("test", "012-345", 7).errors().isEmpty() shouldBe true
        }

        "no hyphen" {
            BSBField("test", "012345", 6).errors().size shouldBe 1
        }

        "contains non-integers" {
            BSBField("test", "012-34a", 7).errors().size shouldBe 1
        }

        "too many integers" {
            BSBField("test", "0123-456", 8).errors().size shouldBe 1
        }

        "too few integers" {
            BSBField("test", "012-34", 6).errors().size shouldBe 1
        }

        "too many hyphens" {
            BSBField("test", "0-2-345", 6).errors().size shouldBe 1
        }
    }
}
