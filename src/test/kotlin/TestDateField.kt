import fyi.aba.lib.DateField
import io.kotlintest.matchers.shouldBe
import io.kotlintest.specs.StringSpec

class TestDateField : StringSpec() {
    init {
        "1st January '15" {
            DateField("test", "010115", 6).errors().isEmpty() shouldBe true
        }

        "32nd December '14" {
            DateField("test", "321214", 6).errors().size shouldBe 1
        }

        "contains dashes" {
            DateField("test", "01-01-15", 8).errors().size shouldBe 1
        }
    }
}
