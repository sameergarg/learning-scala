package stepdefs

import java.util.concurrent.TimeUnit

import cucumber.api.scala.{EN, ScalaDsl}
import org.openqa.selenium.By
import org.openqa.selenium.firefox.FirefoxDriver
import org.scalatest.Matchers

/**
 * Created by sameer on 13/05/15.
 */
class SearchGoogleStepDefs extends ScalaDsl with EN with Matchers {

  val driver = new FirefoxDriver()
  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)

  Given("""^I have navigated to google$"""){ () =>
    driver.navigate().to("http://www.google.co.uk")
  }

  When("""^I search for "(.*?)"$"""){ (searchTerm:String) =>
    driver.findElement(By.id("lst-ib")).sendKeys(searchTerm)
    driver.findElement(By.name("btnG")).click();
  }

  Then("""^the page title should be "(.*?)"$"""){ (title:String) =>
    driver.getTitle.shouldEqual(title)
  }
}
