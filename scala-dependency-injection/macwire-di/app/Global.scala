import com.softwaremill.macwire.{MacwireMacros, Macwire}
import play.api.GlobalSettings

object Global extends GlobalSettings with Macwire {

  val wired = wiredInModule(new GreetingModule {})

  override def getControllerInstance[A](controllerClass: Class[A]): A = {
    wired.lookupSingleOrThrow(controllerClass)
  }
}
