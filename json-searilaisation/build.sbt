name := "json-searilaisation"

version := "1.0"

scalaVersion := "2.11.8"

val json4sNative = "org.json4s" % "json4s-native_2.11" % "3.4.0"
val json4sJackson = "org.json4s" % "json4s-jackson_2.11" % "3.4.0"

val specs2 = "org.specs2" %% "specs2-core" % "3.0" % "test"
val specs2Matcher = "org.specs2" %% "specs2-matcher-extra" % "3.0" % "test"

libraryDependencies ++= Seq(json4sNative, json4sJackson, specs2, specs2Matcher)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

scalacOptions in Test ++= Seq("-Yrangepos")