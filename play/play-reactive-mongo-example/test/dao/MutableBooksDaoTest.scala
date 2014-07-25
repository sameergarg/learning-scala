package dao

import com.github.simplyscala.{MongodProps, MongoEmbedDatabase}
import model.Book
import org.scalatest.BeforeAndAfter
import play.api.libs.json.{Format, Json}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

class MutableBooksDaoTest extends org.scalatest.FunSuite with MongoEmbedDatabase with BeforeAndAfter {

  var mongoProps: MongodProps = null

  override protected def before(fun: => Any): Unit = {
    mongoProps = mongoStart()
  }

  override protected def after(fun: => Any): Unit = {
    mongoStop(mongoProps)
  }


  test("create a book") {
    val booksDao = new BooksDao()
    Await.result(booksDao.deleteById("1"), 10 seconds)

    val book = booksDao.create(Book("1", "Harry Potter"))

    assert(Await.result(book, 10 seconds) != null)

    val books: Future[List[Book]] = booksDao.findAll()

    //val books: Future[List[Book]] =  booksDao.findAll()
    assert(Await.result(books, 10 seconds).isEmpty == false)
  }

  /*test("find all books") {
    val booksDao = new BooksDao()
    //Await.result(booksDao.deleteById("2"), 10 seconds)

    val books: Future[List[Book]] = booksDao.create(Book("2", "Harry Potter")).flatMap { book =>
      booksDao.findAll()
    }
    //val books: Future[List[Book]] =  booksDao.findAll()
    assert(Await.result(books, 10 seconds).isEmpty == false)

  }*/

  class BooksDao extends MongoDocumentsDao[String, Book] {

    override val collectionName: String = "books"

    override implicit val formatter: Format[Book] = Json.format[Book]

  }


}
