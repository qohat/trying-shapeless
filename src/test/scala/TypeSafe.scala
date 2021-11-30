import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import shapeless.syntax.typeable._
import shapeless.TypeCase

class TypeSafe extends AnyFunSuite with Matchers {
  test("TypeSave casting") {
    val l: Any = List(Vector("foo", "bar", "baz"), Vector("wibble"))
    l.cast[List[Vector[String]]] should be(Some(List(Vector("foo", "bar", "baz"), Vector("wibble"))))
    l.cast[List[Vector[Int]]] should be(None)
    l.cast[List[List[String]]] should be(None)
  }

  val `List[String]` = TypeCase[List[String]]
  val `List[Int]` = TypeCase[List[Int]]
  val l = List(1, 2, 3)

  val result = (l: Any) match {
    case `List[String]`(List(s, _*)) => s.length
    case `List[Int]`(List(i, _*)) => i + 1
  }

  test("Extractor Typeable") {
    result should be(2)
  }
}
