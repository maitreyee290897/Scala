import scala.util.Random
case class Student(studentName: String, studentSurname: String)
case class StudentWithId(studentId: String, studentName: String, studentSurname: String)
final case class StudentId(value: String = Random.alphanumeric.take(8).foldLeft("")((result, c) => result + c))