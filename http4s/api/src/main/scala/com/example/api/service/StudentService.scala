import cats.effect.IO
import scala.collection.mutable.HashMap

trait StudentController{

  def addStudent(student: Student): IO[StudentId]
  def getStudent(id: StudentId): IO[Option[StudentWithId]]
  def updateStudent(id: StudentId, student: Student): IO[Either[String, Student]]
  def deleteStudent(id: StudentId): IO[Either[String, Student]]

}
object StudentController{

  class StudentService extends StudentController {

    //storing in memory itself instead of database
    val storage = HashMap[StudentId, Student]().empty

    override def addStudent(student: Student): IO[StudentId] = IO{
      val studentId = StudentId()
      storage.put(studentId, student)
      studentId
    }

    override def getStudent(id: StudentId): IO[Option[StudentWithId]] = IO{
      storage.get(id).map(student => StudentWithId(id.value, student.studentName,student.studentSurname))
    }

    override def updateStudent(id: StudentId, student: Student): IO[Either[String, Student]] = {
      for {
        bookOpt <- getStudent(id)
        _ <- IO(bookOpt.toRight(s"Book with ${id.value} not founddddd"))
        updatedBook = storage.put(id, student)
          .toRight(s"Book with ${id.value} not found")
      } yield updatedBook
    }
    override def deleteStudent(id: StudentId): IO[Either[String, Student]] =
      for {
        removedStudent <- IO(storage.remove(id))
        result = removedStudent.toRight(s"Student with ${id.value} not found")
      } yield result

  }
}