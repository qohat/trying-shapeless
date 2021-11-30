import org.scalatest.matchers.should.Matchers
import org.scalatest.funsuite.AnyFunSuite

import shapeless.syntax.std.tuple._
import shapeless.{::, HNil}

class Tuples extends AnyFunSuite with Matchers {

  test("Tuple Head, Tail, drop, take, split, prepend, append, concatenate") {

    (23, "foo", true).head shouldBe 23

    (23, "foo", true).tail shouldBe ("foo", true)

    (23, "foo", true).drop(2) shouldBe (Tuple1(true))

    (23, "foo", true).take(2) shouldBe (23, "foo")

    (23, "foo", true).split(1) shouldBe (Tuple1(23), ("foo", true))

    23 +: ("foo", true) shouldBe (23, "foo", true)

    (23, "foo") :+ true shouldBe (23, "foo", true)

    (23, "foo") ++ (true, 2.0) shouldBe (23, "foo", true, 2.0)
  }

  /*test("Tuple map and fmap") {
    import option._
    import shapeless.ops.tuple.Mapper._

    val l = (23, "foo", true) map option
    l shouldBe (Some(23), Some("foo"), Some(true))

    val l1 = ((23, "foo"), (), (true, 2.0)) flatMap identity
    l1 shouldBe (23, "foo", true, 2.0)
  }

  import shapeless.poly._

  object option extends (Id ~> Option) {
    def apply[T](t: T) = Option(t)
  }*/

  /*test("Fold") {
    (23, "foo", (13, "wibble")).foldLeft(0)(addSize) should be(11)
  }*/

  test("Product elements") {
    (23, "foo", true).productElements should be(23 :: "foo" :: true :: HNil)
  }

  test("List conversion") {
    (23, "foo", true).toList should be(List(23, "foo", true))
  }

  test("Zipper") {
    import shapeless.syntax.zipper._
    val l = (23, ("foo", true), 2.0).toZipper.right.down.put("bar").root.reify
    l should be(23, ("bar", true), 2.0)
  }
}
