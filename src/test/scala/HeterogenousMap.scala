import org.scalatest.matchers.should.Matchers
import shapeless.HMap
import org.scalatest.funsuite.AnyFunSuite
import shapeless.HNil
import shapeless.syntax._

class HeterogenousMap extends AnyFunSuite with Matchers {
  class BiMapIS[K, V]
  implicit val intToString = new BiMapIS[Int, String]
  implicit val stringToInt = new BiMapIS[String, Int]

  val hm = HMap[BiMapIS](23 -> "foo", "bar" -> 13)

  test("get from map") {
    hm.get(23) should be(Some("foo"))
    hm.get("bar") should be(Some(13))
  }

  /*test("Mapping a HMap") {
    val l = 23 :: "bar" :: HNil
    val m = l map hm
    m should be("foo" :: 13 :: HNil)
  }*/
}
