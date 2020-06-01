case class Money(dollars: Int, cents: Int)
trait Data {
  val balance = Money(102, 44)
  val salary = Money(320, 0)
  val balances: Map[String, Money] = Map(
    "James" -> Money(212, 98),
    "Jimmy" -> Money(43, 44)
  )

  val salaries: Map[String, Money] = Map(
    "James" -> Money(500, 98),
    "Jimmy" -> Money(500, 44)
  )

  val marbles: Map[String, Int] = Map(
    "James" -> 4,
    "Jimmy" -> 5
  )

  val won: Map[String, Int] = Map(
    "James" -> 2,
    "Jimmy" -> 1
  )
}

object Example1Semigroup extends App with Data {

  trait Addable[T] {
    def add(a: T, b: T): T
  }

  implicit val addInt = new Addable[Int] {
    override def add(a: Int, b: Int): Int = a + b
  }

  implicit val addMoney = new Addable[Money] {
    override def add(a: Money, b: Money): Money = {
      Money(a.dollars + b.dollars + ((a.cents + b.cents) / 100),
        (a.cents + b.cents) % 100)
    }
  }

  // [V: Addable] is shothand of (implicit addable: Addable[A])
  implicit def addMap[K, V: Addable] = new Addable[Map[K, V]] {
    override def add(a: Map[K, V], b: Map[K, V]): Map[K, V] = {
      a.foldLeft(b) {
        case (acc, (x, y)) =>
          acc + (x -> acc.get(x).map(implicitly[Addable[V]].add(_, y)).getOrElse(y))
      }
    }
  }

  def add[A: Addable](a: A, b: A)(implicit addable: Addable[A]): A = addable.add(a, b)

  println(s"Salary credit in you account xxxxxxx ${add(balance, salary)}")
  println(s"Salary transfer to all employees ${add(balances, salaries)}")
  println(s"Your game marbles balance is: ${add(marbles, won)}")
}


