import com.softwaremill.macwire.{InstanceLookup, Macwire}
import play.api.GlobalSettings

object Global extends GlobalSettings with Macwire {

  val instanceLookup = InstanceLookup(valsByClass(new GreetingModule {}))

  override def getControllerInstance[A](controllerClass: Class[A]): A = {
    instanceLookup.lookupSingleOrThrow(controllerClass)
  }
}
