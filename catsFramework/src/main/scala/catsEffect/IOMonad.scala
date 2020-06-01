import cats.effect._

object xyz extends App {
  //https://medium.com/@alandevlin7/pure-functional-rest-api-with-http4s-7a38782a2a99
  // side effecting function toConsole
  def toConsole(input: String): Unit = println(input)
  toConsole("hi")

  // return type is IO[Unit] so side effect code now wrapped in IO
  // and cannot evaluate unless and until we tell it so.
  def toConsoleIo(input: String): IO[Unit] = IO(println(input))
  toConsoleIo("Maitreyee")
  // to run the above code use unsafeRunSync()
  toConsoleIo(("Maitryeee")).unsafeRunSync()

  // making referential transparency
  def demo(f1:Unit,f2:Unit) = ()
  var x = println("heyy")
  demo(x ,x);
    // we get same result
  demo(print("heyy"), print("heyy"))
  var x1 = toConsoleIo(("heyy")).unsafeRunSync()
  demo(x1 , x1)

  //IO instances are stack safe
  def calculate(start: Int, end: Int): IO[Int] = {
    IO(start).flatMap { x =>
      if (x == end) IO(x) else calculate(start + 1, end)
    }
  }

  calculate(0, 10000000).flatMap(x => IO(println(x))).unsafeRunSync()

  //lift a number (pure value) into IO and compose it with another IO that wraps a side a effect in a safe manner
  // IO.pure is eagerly evaluated,
  IO.pure(25).flatMap(n => IO(println(s"Number is: $n")))

  //  reading / writing from / to the console, which on top of the JVM uses blocking I/O, so their execution is immediate:
  def putStrlLn(value: String) = IO(println(value))
  val readLn = IO(scala.io.StdIn.readLine)
  // And then we can use that to model interactions with the console in a purely functional way:
  for {
    _ <- putStrlLn("What's your name?")
    n <- readLn
    _ <- putStrlLn(s"Hello, $n!")
  } yield ()

}