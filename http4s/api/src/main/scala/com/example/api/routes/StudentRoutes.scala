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

  def routes(studentRepo: StudentController): HttpRoutes[IO] = {

    val dsl = new Http4sDsl[IO]{}
    import dsl._

    HttpRoutes.of[IO] {
      case req @ POST -> Root / "student" =>
        print(req.body)
        req.decode[Student] { student =>
          studentRepo.addStudent(student) flatMap(id =>
            Created(Json.obj(("id", Json.fromString(id.value))))
            )
        }

      case _ @ GET -> Root / "student" / id =>
        studentRepo.getStudent(StudentId(id)) flatMap {
          case None => NotFound()
          case Some(student) => Ok(student)
        }

      case req @ PUT -> Root / "student" / id =>
        req.decode[Student] { student =>
          println("hiiiii",StudentId(id))
          studentRepo.updateStudent(StudentId(id), student) flatMap {
            case Left(message) => NotFound(errorBody(message))
            case Right(_) => Ok()
          }
        }
      case _ @ DELETE -> Root / "student" / id =>
        studentRepo.deleteStudent(StudentId(id)) flatMap {
          case Left(message) => NotFound(errorBody(message))
          case Right(_) => Ok()
        }
    }
  }

}