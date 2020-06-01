// Monoid implementation
import cats.kernel.Monoid

case class Weight(kg: Int, grams: Int)

object MonoidWeight extends App {

  implicit val moneyMonoid = new Monoid[Weight] {
    override def empty: Weight = Weight(0, 0)

    override def combine(x: Weight, y: Weight): Weight = {
      Weight(x.kg + y.kg + ((x.grams + y.grams) / 1000),
        (x.grams + y.grams) % 1000)
    }
  }

  val prevWeight = List(Weight(3, 4), Weight(34, 5), Weight(12, 0))

  def totalWeight(expenses: List[Weight])(implicit m: Monoid[Weight]): Weight = {
    expenses.foldLeft(m.empty){
      case (acc, money) => m.combine(acc, money)
    }
  }

  println(s"weight : ${totalWeight(prevWeight)}")
}