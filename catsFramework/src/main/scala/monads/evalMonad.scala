import cats.Eval
object EvalMonad extends App {
  val type1 = {
    println("function 1")
    math.random
  }

  def type2 = {
    println("function 2")
    math.random
  }

  lazy val type3 = {
    println("function 3")
    math.random
  }
  val now = Eval.now(math.random + 1000)
  val later = Eval.later(math.random + 2000)
  val always = Eval.always(math.random + 3000)

  def factorial(n: BigInt): Eval[BigInt] =
    if (n == 1) {
      Eval.now(n)
    } else {
      Eval.defer(factorial(n - 1).map(_ * n))
    }

  factorial(50000).value
}