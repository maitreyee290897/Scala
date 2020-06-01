import StudentController.StudentService
import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.server.Router
import org.http4s.implicits._

import cats.implicits._

import org.http4s.server.blaze._
import scala.concurrent.ExecutionContext.global

object Main extends IOApp {

  private val StudentController : StudentController = new StudentService

  val httpRoutes = Router[IO](
    "/" -> StudentRoutes.routes(StudentController)
  ).orNotFound


   def run(args: List[String]): IO[ExitCode] = {

    BlazeServerBuilder[IO](global)
      .bindHttp(9000, "0.0.0.0")
      .withHttpApp(httpRoutes)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
