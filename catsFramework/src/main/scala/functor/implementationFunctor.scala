object abc extends App {

  // functor
  case class Bag[A](content: A) {
    def map[B](f: A => B): Bag[B] = Bag(f(content))

    def apply(content: A): Bag[A] = new Bag(content)
  }

  // class
  case class Sugar(weight: Double)

  // map function
  def half = (sugar: Sugar) => Sugar(sugar.weight / 2)

  val sugarBag: Bag[Sugar] = Bag(Sugar(1)) //Bag functor of type sugar

  val halfSugarBag: Bag[Sugar] = sugarBag.map(sugar => half(sugar))

  print(halfSugarBag)

  // identity
  def identity[A](x: A): A = x
  val sugarBag1 = Bag(Sugar(1))
  println(sugarBag1.map(identity) == sugarBag1)
// composition
  def f = (sugar: Sugar) => Sugar(sugar.weight + 1)
  def g = (sugar: Sugar) => Sugar(sugar.weight * 2)

  println(sugarBag.map(f).map(g) == sugarBag.map(x => g(f(x))))
}