import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import shapeless._

class Lenses extends AnyFunSuite with Matchers {

  case class Address(street: String, city: String, postcode: String)
  case class Person(name: String, age: Int, address: Address)

  // Some lenses over Person/Address ...
  val nameLens = lens[Person] >> Symbol("name")
  val ageLens = lens[Person] >> Symbol("age")
  val addressLens = lens[Person] >> Symbol("address")
  val streetLens = lens[Person] >> Symbol("address") >> Symbol("street")
  val cityLens = lens[Person] >> Symbol("address") >> Symbol("city")
  val postcodeLens = lens[Person] >> Symbol("address") >> Symbol("postcode")

  val person = Person("Joe Grey", 37, Address("Southover Street", "Brighton", "BN2 9UA"))

  test("Lens in person age") {
    ageLens.get(person) should be(37)

    val updatedPerson = ageLens.set(person)(38)
    updatedPerson.age should be(38)

    val modifiedPerson = ageLens.modify(person)(_ + 1)
    modifiedPerson.age should be(38)
  }

  test("Lens in person street") {
    streetLens.get(person) should be("Southover Street")

    val updatedPerson = streetLens.set(person)("Montpelier Road")
    updatedPerson.address.street should be("Montpelier Road")
  }
}
