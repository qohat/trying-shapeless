import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import shapeless.{::, HNil}

class HeterogenousList extends AnyFunSuite with Matchers {

  import CovariantHelper._

  test("Zipper right reify ") {
    import shapeless.syntax.zipper._

    val l = 1 :: "foo" :: 3.0 :: HNil
    l.toZipper.right.put(("wibble", 45)).reify should be(1 :: ("wibble", 45) :: 3.0 :: HNil)
    l.toZipper.right.delete.reify should be(1 :: 3.0 :: HNil)
  }

  test("TypeTag") {
    import scala.reflect.runtime.universe._

    implicitly[TypeTag[APAP]].tpe.typeConstructor <:< typeOf[FFFF] should be(true)
    apap.isInstanceOf[FFFF] should be(true)
    apap.unify.isInstanceOf[FFFF] should be(true)
  }

  test("List conversion") {
    apap.toList should be(List(Apple(), Pear(), Apple(), Pear()))
  }

  test("Option") {

    import shapeless.syntax.typeable._
    val ffff: FFFF = apap.unify

    ffff.cast[APAP] shouldBe (Some(Apple() :: Pear() :: Apple() :: Pear() :: HNil))
  }

}

object CovariantHelper {

  trait Fruit
  case class Apple() extends Fruit
  case class Pear() extends Fruit

  type FFFF = Fruit :: Fruit :: Fruit :: Fruit :: HNil
  type APAP = Apple :: Pear :: Apple :: Pear :: HNil

  val a: Apple = Apple()
  val p: Pear = Pear()

  val apap: APAP = a :: p :: a :: p :: HNil

}
