import cats.syntax.either._

object EitherMonad extends App {
  val ok: Either[Error, String] =
    Right("right")
  val error: Either[Error, String] =
    Left(new Error("error!"))

  def uncertainTask(x: Float): Either[Error, String] =
    if (x > 0.5)
      Right("right")
    else
      Left(new Error("error"))

  println(uncertainTask(0.6f))

  val a = 3.asRight[String]
  val b = 4.asRight[String]

  def countPositive(nums: List[Int]) = nums.foldLeft(0.asRight[String]) { (accumulator, num) =>
    if (num > 0) {
      accumulator.map(_ + 1)
    } else {
      Left("Stopping!")
    }
  }

  println(countPositive(List(2, 3, 4)))
  println(countPositive(List(2, -1, 1)))
}