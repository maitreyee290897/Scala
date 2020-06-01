// Type class implementation
object typeClass extends App{
  trait Printable[A] {
    def format(value: A): String
  }
  object PrintableInstances {
    implicit val stringPrintable = new Printable[String] {
      def format(input: String) = input
    }
    implicit val intPrintable = new Printable[Int] {
      def format(input: Int) = input.toString
    }
  }

  object Printable {
    def format[A](input: A)(implicit p: Printable[A]): String =
      p.format(input)
    def print[A](input: A)(implicit p: Printable[A]): Unit = println(format(input))
  }
  case class Shop(name: String, city: String, country: String)
  import PrintableInstances._
  implicit val catPrintable = new Printable[Shop] {
    def format(shop: Shop) = {
      val name = Printable.format(shop.name)
      val city = Printable.format(shop.city)
      val country = Printable.format(shop.country)
      s"$name is in $city , $country ."
    } }

  val shop = Shop("magic planet", "Sharjah", "UAE")

  Printable.print(shop)

  // using implicit class
  object PrintableSyntax {
    implicit class PrintableOps[A](value: A) {
      def format(implicit p: Printable[A]): String = p.format(value)
      def print(implicit p: Printable[A]): Unit =
        println(format(p))
    } }

  import PrintableSyntax._
  Shop("magic planet", "Ajman", "UAE").print
}
