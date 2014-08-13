name := """macwire-di"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

libraryDependencies += "com.softwaremill.macwire" %% "macros" % "0.6"

libraryDependencies += "com.softwaremill.macwire" %% "runtime" % "0.6"
