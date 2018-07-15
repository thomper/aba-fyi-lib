import fyi.aba.lib.TextField
import io.kotlintest.specs.StringSpec
import io.kotlintest.matchers.shouldBe

class TestTextField : StringSpec() {
    init {
        "length 0 required 1" {
            TextField("test", "", 1).errors().size shouldBe 1
        }

        "length 1 required 0" {
            TextField("test", "a", 0).errors().size shouldBe 1
        }

        "length 0 required 0" {
            TextField("test", "", 0).errors().isEmpty() shouldBe true
        }

        "length 1 required 1" {
            TextField("test", "a", 1).errors().isEmpty() shouldBe true
        }

        "length 121 required 120" {
            TextField("test", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                    120).errors().size shouldBe 1
        }

        "length 120 required 120" {
            TextField("test", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa",
                    120).errors().isEmpty() shouldBe true
        }
    }
}
