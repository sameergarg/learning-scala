package dao

import play.modules.reactivemongo.json.collection.JSONCollection
import reactivemongo.api.MongoDriver
import scala.concurrent.ExecutionContext.Implicits.global

object Connection {

  // gets an instance of the driver
  val driver = new MongoDriver
  // (creates an actor system)
  val connection = driver.connection(List("localhost"))
  // Gets a reference to the database "test"
  val db = connection("test")


  def getCollection(collectionName: String): JSONCollection = {
    // Gets a reference to the collection "acoll"
    // By default, you get a BSONCollection.
    db(collectionName)
  }


}
