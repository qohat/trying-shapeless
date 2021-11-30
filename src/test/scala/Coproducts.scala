import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import shapeless._, shapeless.syntax.singleton._
import union._

class Coproducts extends AnyFunSuite with Matchers {

  type ISB = Int :+: String :+: Boolean :+: CNil

  val isb = Coproduct[ISB]("foo")

  test("Coproducts") {

    isb.select[Int] should be(None)

    isb.select[String] should be(Some("foo"))
  }

  test("Coproducts Symbol") {
    type U = Union.`'i -> Int, 's -> String, 'b -> Boolean`.T

    val u = Coproduct[U](Symbol("s") ->> "foo") // Inject a String into the union at label 's

    u.get(Symbol("i")) should be(None)
    u.get(Symbol("s")) should be(Some("foo"))
    u.get(Symbol("b")) should be(None)
  }

  /*test("Coproducts map") {

    val m = isb map sizeM

    m.select[(String, Int)] should be(Some("foo", 3))
  }*/

}
