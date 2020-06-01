object GfG extends App
{

    // Creating list of numbers
    val list1 = List(1, 2, 3, 4)
    val list2 = List(5, 6, 7, 8)

    // Applying 'flatMap' and 'map'
    val z = list1 flatMap { q => list2 map {
      r => q + r
    }
    }
    // Displays output List(6, 7, 8, 9, 7, 8, 9, 10, 8, 9, 10, 11, 9, 10, 11, 12)
    println(z)

  case class Bag[A](content: A) {
    // def map[B](f: A => B): Bag[B] = Bag(f(content))
    def flatMap[B](f: A => Bag[B]): Bag[B] = f(content)
   // def apply(content: A): Bag[A] = new Bag(content)
  }
  case class Sugar(weight: Double)
  val sugarBag: Bag[Sugar] = Bag(Sugar(1))
  def double = (sugar: Sugar) => Bag(Sugar(sugar.weight * 2))
  val doubleSugarBag = sugarBag.flatMap(sugar => double(sugar))
  println(doubleSugarBag)
}