package services

import java.util.Calendar

class GreetingService {

  def greetingMessage(language: String) = language match {
    case "it" => "Messi"
    case _ => "Hello"
  }

}
