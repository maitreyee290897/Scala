import cats.Functor
import cats.instances.list._
import cats.instances.option._
import cats.instances.function._
import cats.syntax.functor._

object myFunctor extends App {
  //F[_]-type constructor
  trait Functor[F[_]] {
    def map[A,B](fa: F[A])(f: A=> B): F[B]
  }
  case class Sack[A](value: A)
  // instance of type class
  object Functor {
    def apply[F[_]](implicit M: Functor[F]): Functor[F] = M

    //defining functor for custom type, here sack is a type constructor
    implicit val bagFunctor: Functor[Sack] =
      new Functor[Sack] {
        def map[A, B](fa: Sack[A])(f: A => B): Sack[B] = Sack(f(fa.value))
      }
  }
  val bag = new Sack(1)
  val b = Functor[Sack]
  def f = (a:Int)=> a+1
  println(b.map(bag)(f))

  //b.map(bag)(n => n + 1)
}
