import cats.kernel.Monoid

object  PersonMonoid extends App {

  case class Person(username: String, followers: Int) extends Ordered[Person] {
    override def compare(that: Person): Int = {
      val c = this.followers - that.followers
      if(c == 0) this.username.compareTo(that.username) else c
    }
  }

  implicit val personMonoid = new Monoid[Person] {
    override def empty: Person = Person("MinUser", Int.MinValue)

    override def combine(x: Person, y: Person): Person = {
      if(x.compareTo(y) >= 1) x else y
    }
  }

  case class Max(user: Person) {
    def +(usr: Max)(implicit m: Monoid[Person]): Max = {
      Max(m.combine(this.user, usr.user))
    }
  }

  val person1 = Person("a", 132)
  val person2= Person("b", 575)
  val person3 = Person("d", 387)
  val person4 = Person("e", 10640)
  val person5 = Person("f", 20421)

  val winner: Max = Max(person1) + Max(person2) + Max(person3) + Max(person4) + Max(person5)

  println(s"Winner Is: $winner")

}