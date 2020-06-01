import java.io.FileReader
import java.io.FileNotFoundException
import java.io.IOException

object demo extends App {
  // if else
  val filename =
    if (!args.isEmpty) args(0) else "default.txt"

  println(filename)

  // while loop to find gcd
  def gcdLoop(x: Long, y: Long): Long = {
    var a = x
    var b = y
    while (a != 0) {
      val temp = a
      a=b%a
      b = temp
    }
    b
  }
  println(gcdLoop(10,4))

  // do while
  var line = 0
  do {
    line = line+1
    println("Read: "+ line)
  } while (line != 5)

  // for loop
  val filesHere = (new java.io.File(".")).listFiles
  for (file <- filesHere)
  println(file)

  //filtering
  val filesHere1 = (new java.io.File(".")).listFiles
  for (file <- filesHere if file.getName.endsWith(".scala"))
    println(file)

  // try catch
  try {
    val f = new FileReader("input.txt") // Use and close file
    println(f.read())
  } catch {
    case ex: FileNotFoundException => // Handle missing file
    case ex: IOException => // Handle other I/O error
  }
// case
val firstArg = if (!args.isEmpty) args(0) else ""
  val friend =
    firstArg match {
      case "salt" => "pepper"
      case "chips" => "salsa"
      case "eggs" => "bacon"
      case _ => "huh?"
    }
  println(friend)

}