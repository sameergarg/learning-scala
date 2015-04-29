import cucumber.api.CucumberOptions
import org.junit.runner.RunWith
import cucumber.api.junit.Cucumber

@RunWith(classOf[Cucumber])
@CucumberOptions(
  features = Array("test/features"),
  glue = Array("test/features/step") )
class RunCukeTests