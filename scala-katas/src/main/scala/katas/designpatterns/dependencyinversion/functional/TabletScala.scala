package katas.designpatterns.dependencyinversion.functional

/**
 *
 */
object TabletScala extends App {
  
  def plugin(charger: => String): String = {
    charger
  }

  println(plugin("charging"))

  }


