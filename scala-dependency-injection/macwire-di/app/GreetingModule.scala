import controllers.GreeterController
import services.{ServicesModule, GreetingService}

trait GreetingModule extends ServicesModule {

  import com.softwaremill.macwire.MacwireMacros._

  lazy val greeterController = wire[GreeterController]

}
