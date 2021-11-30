import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers
import scala.util.Try
class TypeChecking extends AnyFunSuite with Matchers {
  import shapeless.test.illTyped

  test("Type checking") {
    val matchedTypes = Try(assertTypeError("illTyped { \"val a: Int = 1\" }")).isSuccess
    matchedTypes should be(true)

    val mismatchedTypes = Try(assertTypeError("illTyped { \"val a: String = 1\" }")).isSuccess
    mismatchedTypes should be(false)
  }

}
