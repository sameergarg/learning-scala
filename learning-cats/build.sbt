name := "learning-cats"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.1.0"
libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"

scalacOptions += "-Ypartial-unification"

