import fyi.aba.lib.BSBFillerField
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class TestBSBFillerField : StringSpec() {
    init {
        "valid" {
            BSBFillerField("test", "999-999", 7).errors().isEmpty() shouldBe true
        }

        "invalid" {
            BSBFillerField("test", "123-456", 7).errors().size shouldBe 1
        }
    }
}
