import cats.Functor
import cats.instances.list._
import cats.instances.option._
import cats.instances.function._
import cats.syntax.functor._

object CustomMonad extends App {
  case class Sack[A](value: A)
  trait Monad[M[_]] {
    def pure[A](a: A): M[A]
    def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
  }
  object Monad {
    def apply[F[_]](implicit M: Monad[F]): Monad[F] = M
    //defining our own  monad for custom type, here bag is a type constructor
    implicit val sackMonad: Monad[Sack] =
      new Monad[Sack] {
        def flatMap[A, B](fa: Sack[A])(f: A => Sack[B]): Sack[B] = f(fa.value)
        def pure[A](fa: A): Sack[A] = Sack(fa)
      }
  }
  val bag = new Sack(1)
  val b = Monad[Sack]
  def f = (a:Int)=> a+1
  println(b.flatMap(bag)(n => Sack(n + 1)))
}
