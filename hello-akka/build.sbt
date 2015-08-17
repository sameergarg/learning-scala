name := "hello-akka"

version := "1.0"

scalaVersion := "2.11.7"


libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.12",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.12" % "test",
  "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"
)