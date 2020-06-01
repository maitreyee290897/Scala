import cats.effect.IO
import io.circe.Json
import org.http4s.HttpRoutes
import io.circe.generic.auto._
import org.http4s.circe.CirceEntityCodec._
import org.http4s.dsl.Http4sDsl

object StudentRoutes{
  private def errorBody(message: String) = Json.obj(
    ("message", Json.fromString(message))
  )

  def routes(bookRepo: StudentDao): HttpRoutes[IO] = {

    val dsl = new Http4sDsl[IO]{}
    import dsl._

    HttpRoutes.of[IO] {
      case req @ POST -> Root / "books" =>
        print(req.body)
        req.decode[Student] { book =>
          bookRepo.addStudent(book) flatMap(id =>
            Created(Json.obj(("id", Json.fromString(id.value))))
            )
        }

      case _ @ GET -> Root / "books" / id =>
        bookRepo.getStudent(StudentId(id)) flatMap {
          case None => NotFound()
          case Some(book) => Ok(book)
        }

      case _ @ DELETE -> Root / "books" / id =>
        bookRepo.deleteStudent(StudentId(id)) flatMap {
          case Left(message) => NotFound(errorBody(message))
          case Right(_) => Ok()
        }
    }
  }

}