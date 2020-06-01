// Cats with type class
import cats.Show
import cats.instances.int._ // for Show
import cats.instances.string._ // for Show
import cats.syntax.show._

object catsWithTypeClass extends App {

  case class Shop(name: String,  location: String)

  implicit val catShow: Show[Shop] = Show.show[Shop] { cat =>
    val name = cat.name.show
    val location = cat.location.show
    s"$name is in $location ."
  }

  println(Shop("Magic planet", "Abu Dhabi").show)
}