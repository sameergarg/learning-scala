package stepdefs

import cucumber.api.CucumberOptions
import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("test/resources/features"),
  glue = Array("stepdefs"),
  format = Array("pretty", "html:target/cucumber-report"),
  tags = Array("@wip","@completed")
)
class RunCucumber {
}
