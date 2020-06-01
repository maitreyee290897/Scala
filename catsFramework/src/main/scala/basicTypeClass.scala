// basic implementation of type class
object basic extends App {

  sealed trait Json

  final case class JsObject(get: Map[String, Json]) extends Json

  final case class JsString(get: String) extends Json

  final case class JsNumber(get: Double) extends Json

  case object JsNull extends Json

  trait JsonWriter[A] {
    def write(value: A): Json
  }

  final case class Shop(name: String, location: String)

  object JsonWriterInstances {
    implicit val stringWriter: JsonWriter[String] =
      new JsonWriter[String] {
        def write(value: String): Json = JsString(value)
      }

    implicit val personWriter: JsonWriter[Shop] =
      new JsonWriter[Shop] {
        def write(value: Shop): Json =
          JsObject(Map(
            "name" -> JsString(value.name),
            "location" -> JsString(value.location)
          ))
      }
  }

  object Json {
    def toJson[A](value: A)(implicit w: JsonWriter[A]): Json = w.write(value)
  }

  import JsonWriterInstances._

 print(Json.toJson(Shop("City Center", "Sharjah, Al Nahada, UAE")))
}
