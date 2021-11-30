import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import shapeless.syntax.std.function._
import shapeless.ops.function._
import shapeless.HList
import shapeless.Generic

class Arity extends AnyFunSuite with Matchers {

  test("Abstracting over arity") {
    applyProduct((1, 2))((_: Int) + (_: Int)) should be(3)
    applyProduct((1, 2, 3))((_: Int) * (_: Int) * (_: Int)) should be(6)
  }

  def applyProduct[P <: Product, F, L <: HList, R](p: P)(f: F)(implicit gen: Generic.Aux[P, L], fp: FnToProduct.Aux[F, L => R]) =
    f.toProduct(gen.to(p))
}
