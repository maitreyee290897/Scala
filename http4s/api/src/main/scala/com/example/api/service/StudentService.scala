import cats.effect.IO
import scala.collection.mutable.HashMap

trait StudentDao{

  def addStudent(book: Student): IO[StudentId]
  def getStudent(id: StudentId): IO[Option[StudentWithId]]
  def deleteStudent(id: StudentId): IO[Either[String, Student]]

}
object StudentDao{

  class StudentService extends StudentDao{

    //storing in memory itself instead of database
    val storage = HashMap[StudentId, Student]().empty

    override def addStudent(book: Student): IO[StudentId] = IO{
      val bookId = StudentId()
      storage.put(bookId, book)
      bookId
    }

    override def getStudent(id: StudentId): IO[Option[StudentWithId]] = IO{
      storage.get(id).map(book => StudentWithId(id.value, book.studentName,book.studentSurname))
    }

    override def deleteStudent(id: StudentId): IO[Either[String, Student]] =
      for {
        removedStudent <- IO(storage.remove(id))
        result = removedStudent.toRight(s"Student with ${id.value} not found")
      } yield result

  }
}