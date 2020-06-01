import cats.Functor
import cats.instances.list._ // for Functor
import cats.instances.option._ // for Functor

object ab extends App {
  val list1 = List(1, 2, 3)
  // list1: List[Int] = List(1, 2, 3)
  val list2 = Functor[List].map(list1)(_ * 2)
  // list2: List[Int] = List(2, 4, 6)
  val option1 = Option(123)
  println(list2)
  // option1: Option[Int] = Some(123)
  val option2 = Functor[Option].map(option1)(_.toString)
  // option2: Option[String] = Some("123")
  println(option2)
}