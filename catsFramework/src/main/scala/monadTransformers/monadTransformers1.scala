import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


object App extends App {
    trait Monad[T[_]] {
      def map[A, B](value: T[A])(f: A => B): T[B]

      def flatMap[A, B](value: T[A])(f: A => T[B]): T[B]

      def pure[A](x: A): T[A]
    }
    implicit val futureMonad = new Monad[Future] {
      def map[A, B](value: Future[A])(f: (A) => B) = value.map(f)

      def flatMap[A, B](value: Future[A])(f: (A) => Future[B]) = value.flatMap(f)

      def pure[A](x: A): Future[A] = Future(x)
    }

    case class OptionTransformer[T[_], A](value: T[Option[A]])(implicit m: Monad[T]) {

      def map[B](f: A => B): OptionTransformer[T, B] = {
        OptionTransformer[T, B](m.map(value)(_.map(f)))
      }

      def flatMap[B](f: A => OptionTransformer[T, B]): OptionTransformer[T, B] = {
        val result: T[Option[B]] = m.flatMap(value)(a => a.map(b => f(b).value)
          .getOrElse(m.pure(None)))
        OptionTransformer[T, B](result)
      }
    }

    val first = OptionTransformer(Future(Option(1)))
    val second = OptionTransformer(Future(Option(2)))

  val sum = for {
    firstResult <- first
    secondResult <- second
  } yield {
    firstResult + secondResult
  }

  print(Await.result(sum.value, Duration.Inf))
}