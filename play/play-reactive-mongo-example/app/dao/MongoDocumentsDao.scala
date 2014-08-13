package dao

import model._
import org.slf4j.LoggerFactory
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.core.commands.LastError
import scala.util.parsing.json.JSONObject
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import play.api.libs.json.{Format, JsObject, Json}
import play.modules.reactivemongo.json.BSONFormats._

trait MongoDocumentsDao[S, D <: Document] extends CrudDao[S, D] {

  def logger = LoggerFactory.getLogger(getClass.getName)
  //implicit val formatter: Format[Book] = Json.format[Book]
  implicit val formatter: Format[D]

  val collectionName: String
  
  private lazy val collection = Connection.getCollection(collectionName)

  override def findAll(): Future[List[D]] = collection.find(BSONDocument()).cursor[D].collect[List]()

  override def update(resource: D): Future[Boolean] = ???

  override def findById(id: S): Future[Option[D]] = ???

  override def delete(resource: D): Future[Boolean] = {
    collection.remove(Json.obj("_id" -> resource._id).as[JsObject]).map {
      case LastError(true, _, _, _, Some(doc), _, _) => true
      case _ => false
    }
  }

  override def deleteById(id: String): Future[Boolean] = {
    collection.remove(Json.obj("_id" -> id).as[JsObject]).map {
      case LastError(true, _, _, _, Some(doc), _, _) => true
      case _ => false
    }
  }


}

