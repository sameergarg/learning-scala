package features.steps

import cucumber.api.scala.{EN, ScalaDsl}
import org.fluentlenium.core.filter.FilterConstructor._
import org.scalatest.Matchers
import play.Logger
import play.api.test._

class HelloWorldStepDefinition extends ScalaDsl with EN with Matchers {

  //val webDriverClass = Helpers.HTMLUNIT
  val webDriverClass = Helpers.FIREFOX

  val app = FakeApplication()
  val port = 9000 // or whatever you want

  lazy val browser: TestBrowser = TestBrowser.of(webDriverClass, Some("http://localhost:" + port))
  lazy val server = TestServer(port, app)
  def driver = browser.getDriver()

  Before() { s =>
    // initialize play-cucumber
    //server.start()
  }

  After() { s =>
    // shut down play-cucumber
    //server.stop()
    browser.quit()
  }

  Given("""^my application is running$""") { () =>
    //// Express the Regexp above with the code you wish you had
    Logger.debug("Yeah, application IS running")
  }

  When("""^I go to the "([^"]*)" page$""") { (pageName: String) =>
    //// Express the Regexp above with the code you wish you had
    val pageUrl = pageName match {
      case "start" => controllers.routes.Application.index.url
      case _ => throw new RuntimeException(s"unsupported page: $pageName")
    }
    browser.goTo(pageUrl)
  }

  Then("""^I should see "([^"]*)"$""") { (expectedText: String) =>
    //// Express the Regexp above with the code you wish you had
    val element = browser.find("body", withText().contains(expectedText))
    withClue("Expected text not found in body: " + expectedText) {
      element shouldNot be(empty)
    }
  }
}
