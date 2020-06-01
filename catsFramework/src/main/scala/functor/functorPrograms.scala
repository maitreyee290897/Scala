
import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Random
import cats.instances.function._ // for Functor
import cats.syntax.functor._ // for map
import cats.Functor
import cats.instances.list._ // for Functor
import cats.instances.option._ // for Functor

object o extends App {
  // identity of functors
  println(List(1, 2, 3).map(n => n ))

  println(List(1, 2, 3).map(n => n + 1).map(n => n+2))
  //Functor Laws Iden􏰃ty: fa.map(a => a) == fa
  // Composi􏰃on: fa.map(g(f(_))) == fa.map(f).map(g)

  // future basic
  val future: Future[String] = Future(123).
    map(n => n + 1).map(n => n * 2).map(n => s"${n}!")
  println(Await.result(future, 1.second))

  // future referen􏰃ally transparent.
  val future1 = {
    // Initialize Random with a fixed seed:
    val r = new Random(0L)
    // nextInt has the side-effect of moving to // the next random number in the sequence:
    val x = Future(r.nextInt)
    for {
      a <- x
      b <- x
    } yield (a, b)
  }
  val future2 = {
    val r = new Random(0L)
    for {
      a <- Future(r.nextInt)
      b <- Future(r.nextInt)
    } yield (a, b)
  }
  val result1 = Await.result(future1, 1.second)
  val result2 = Await.result(future2, 1.second)

  print("........")
  print(result1)
  print(result2)

  // single argument functions as functors
  val func =
    ((x: Int) => x.toDouble).
      map(x => x + 1). map(x => x * 2). map(x => s"${x}!")
  println("===========")
  println(func(123))

  import cats.instances.function._ // for Functor
  import cats.syntax.functor._ // for map
  val func1: Int => Double = (x: Int) => x.toDouble
  val func2: Double => Double =
    (y: Double) => y * 2

  println((func1 map func2)(1)) // composition using map
  // res3: Double = 2.0 // composition using map
  println((func1 andThen func2)(1) )// composition using andThen
  // res4: Double = 2.0 // composition using andThen func2(func1(1))

  /*
       functor type class in cats
   */
  val list1 = List(1, 2, 3)
  // list1: List[Int] = List(1, 2, 3)
  val list2 = Functor[List].map(list1)(_ * 2) // list2: List[Int] = List(2, 4, 6)
  val option1 = Option(123)
  // option1: Option[Int] = Some(123)
  val option2 = Functor[Option].map(option1)(_.toString) // option2: Option[String] = Some("123")

  // lift method
  val funcTest = (x: Int) => x + 1
  // func: Int => Int = <function1>
  val liftedFunc = Functor[Option].lift(funcTest)
  println("lift method", liftedFunc(Option(1)))
  // res1: Option[Int] = Some(2)

}

