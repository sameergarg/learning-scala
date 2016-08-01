package katas.xml

import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory

import scala.util.{Failure, Success, Try}

/**
  * Created by samegarg on 01/08/2016.
  */
object XMLValidation extends App {

  Try({
    val in = getClass.getResourceAsStream("/xml/books.xsd")
    val inXmlFile = getClass.getResourceAsStream("/xml/books.xml")

    val schemaLang = "http://www.w3.org/2001/XMLSchema"
    val factory = SchemaFactory.newInstance(schemaLang)
    val schema = factory.newSchema(new StreamSource(in))
    val validator = schema.newValidator()
    validator.validate(new StreamSource(inXmlFile))
  }) match {
    case Failure(ex) => System.err.println(s"Invalid xml ${ex.getMessage}")
    case Success(p) => println("Valid xml")
  }
}


