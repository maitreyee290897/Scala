//def makeEachRow(row: Int) = {
//  for (col <- 1 to 10) yield {
//    val prod = (row * col).toString
//    val padding = " " * (4 - prod.length)
//    padding + prod
//  }
//}
//def makeRow(row: Int) = makeEachRow(row).mkString
//def makeTable() = {
//  val table =
//    for (row <- 1 to 10)
//      yield makeRow(row)
//  table.mkString("\n")
//}
//println(makeTable())
//
//object Curry {
//    def concat(a: String) = (b: String) => a + b;
//}
//println(Curry.concat("Maitreyee")("Gadwe"));
