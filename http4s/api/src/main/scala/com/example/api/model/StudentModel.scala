import scala.util.Random

//case class Book(bookTitle: String, bookAuthor: String)
//case class BookWithId(bookId: String, bookTitle: String, bookAuthor: String)
//final case class BookId(value: String = Random.alphanumeric.take(8).foldLeft("")((result, c) => result + c))

case class Student(studentName: String, studentSurname: String)
case class StudentWithId(studentId: String, studentName: String, studentSurname: String)
final case class StudentId(value: String = Random.alphanumeric.take(8).foldLeft("")((result, c) => result + c))