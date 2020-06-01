import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object AppTransformer extends App {
  case class FutOpt[A](value: Future[Option[A]]) {
    def map[B](f: A => B): FutOpt[B] =
      FutOpt(value.map(optA => optA.map(f)))
    def flatMap[B](f: A => FutOpt[B]): FutOpt[B] =
      FutOpt(value.flatMap(opt => opt match {
        case Some(a) => f(a).value
        case None => Future.successful(None)
      }))
  }

  val first = FutOpt(Future(Option(1)))
  val second = FutOpt(Future(Option(2)))

  val sum = for {
    firstResult <- first
    secondResult <- second
  } yield {
    firstResult + secondResult
  }

  print(Await.result(sum.value, Duration.Inf))
}