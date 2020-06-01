package basics
class Person(var name: String, var age:Int){
  // functionality
  val x = 2
  print("hii")
  def greet(name: String): Unit = println("hhii")

}
class Writer(var name: String,var surname: String){
  def method(): String = this.name + " " + this.surname

}
class Novel(var name:String,var wri: Writer){

}
class counter(var count: Int){
  def currentCount(): Int = count
  def incDec():Int= {
    count = count + 1
    count
  }
  def incDec(num: Int):Int = {
    count  = count +num
    count
  }
}
object oop extends App {
val person = new Person("maitreyee", 29)
  print(person.age)
  val writer = new Writer("mai", "gadwe")
  println(writer.name)
  println(writer.surname)
  var co = new counter(10)
  co.incDec()
  co.incDec(10)
  println(co.count)

}
