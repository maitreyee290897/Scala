// 3
import cats.Eq
import cats.syntax.eq._
import cats.instances.int._ // for Eq
import cats.instances.string._ // for Eq
import cats.instances.option._ // for Eq
object eq extends App {

  final case class Cat(name: String, age: Int, color: String)

  implicit val catEqual: Eq[Cat] = Eq.instance[Cat] { (cat1, cat2) =>
    (cat1.name === cat2.name) && (cat1.age === cat2.age) && (cat1.color === cat2.color)
  }
  val cat1 = Cat("Garfield", 38, "orange and black")

  val cat2 = Cat("Heathcliff", 32, "orange and black")

  println(cat1 === cat2)
  println(cat1 =!= cat2)
  val optionCat1 = Option(cat1)
  val optionCat2 = Option.empty[Cat]
  println(optionCat1 === optionCat2)

  println(optionCat1 =!= optionCat2)

}