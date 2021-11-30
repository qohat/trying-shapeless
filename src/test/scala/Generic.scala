import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import shapeless._

class Generic extends AnyFunSuite with Matchers {

  case class Foo(i: Int, s: String, b: Boolean)

  val fooGen = Generic[Foo]

  val foo = Foo(23, "foo", true)

  test("Converting generics") {
    fooGen.to(foo) should be(23 :: "foo" :: true :: HNil)
    fooGen.from(13 :: "foo" :: true :: HNil).i should be(13)
  }

  import poly._

  // Simple recursive case class family
  sealed trait Tree[T]
  case class Leaf[T](t: T) extends Tree[T]
  case class Node[T](left: Tree[T], right: Tree[T]) extends Tree[T]

  // Polymorphic function which adds 1 to any Int and is the identity
  // on all other values
  object inc extends ->((i: Int) => i + 1)

  val tree: Tree[Int] =
    Node(Leaf(1), Node(Leaf(2), Leaf(3)))

  test("Should apply inc everywhere") {
    everywhere(inc)(tree) should be(
      Node(Leaf(2), Node(Leaf(3), Leaf(4)))
    )
  }

  case class Book(author: String, title: String, id: Int, price: Double)

  import record._

  val bookGen = LabelledGeneric[Book]
  val tapl = Book("Benjamin Pierce", "Types and Programming Languages", 262162091, 44.11)
  val rec = bookGen.to(tapl)

  case class ExtendedBook(author: String, title: String, id: Int, price: Double, inPrint: Boolean)

  import syntax.singleton._

  val bookExtGen = LabelledGeneric[ExtendedBook]

  val extendedBook = bookExtGen.from(rec + (Symbol("inPrint") ->> true))

  test("Labelled Generic") {
    rec(Symbol("price")) should be(44.11)

    val updatedBook = bookGen.from(rec.updateWith(Symbol("price"))(_ + 2.0))

    updatedBook.price should be(46.11)

    extendedBook.inPrint should be(true)
  }

}
