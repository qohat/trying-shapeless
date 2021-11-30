import org.scalatest.funsuite.AsyncFunSuite
import org.scalatest.matchers.should.Matchers

class SingletonLiterals extends AsyncFunSuite with Matchers {
  import shapeless._, shapeless.syntax.singleton._
  import syntax.std.tuple._

  /*val hlist = 23 :: "foo" :: true :: HNil
  val tuple = (23, "foo", true)

  test("Singleton Literals") {
    23.value == 23 should be(true)
    "foo".value == "foo" should be(true)
  }*/

  val (wTrue, wFalse) = (Witness(true), Witness(false))

  type True = wTrue.T
  type False = wFalse.T

  trait Select[B] { type Out }

  def select(b: WitnessWith[Select])(t: b.instance.Out) = t

  test("Select") {

    implicit val selInt = new Select[True] { type Out = Int }
    implicit val selString = new Select[False] { type Out = String }

    select(true)(23) should be(
      23
    )

    // select(true)("foo")
    // error: type mismatch;
    // found   : String("foo")
    // required: Int
    //              select(true)("foo")
    //                           ^

    // select(false)(23)
    // error: type mismatch;
    // found   : Int(23)
    // required: String

    select(false)("foo") should be(
      "foo"
    )
  }

}
