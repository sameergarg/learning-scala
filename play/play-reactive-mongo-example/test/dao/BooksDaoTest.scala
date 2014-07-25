package dao

import com.github.simplyscala.{MongodProps, MongoEmbedDatabase}
import model.{Book}
import org.scalatest.BeforeAndAfter
import play.api.libs.json.{Json, Format}

import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class BooksDaoTest extends org.scalatest.FunSuite with MongoEmbedDatabase with BeforeAndAfter {


  test("create a book") {
    withEmbedMongoFixture() { mongodProps =>
      val booksDao = new BooksDao()
      val book = booksDao.create(Book("1", "Harry Potter"))

      assert(Await.result(book, 10 seconds) != null)
    }
  }

  test("find all books") {
    withEmbedMongoFixture() {
      mongodProps =>
        val booksDao = new BooksDao()
        val books: Future[List[Book]] = booksDao.create(Book("2", "Harry Potter")).flatMap {book =>
          booksDao.findAll()
        }
        //val books: Future[List[Book]] =  booksDao.findAll()

        assert(Await.result(books, 10 seconds).isEmpty == false)

    }
  }

  class BooksDao extends MongoDocumentsDao[String, Book] {

    override val collectionName: String = "books"

    override implicit val formatter: Format[Book] = Json.format[Book]

  }


}
