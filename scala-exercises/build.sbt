name := "scala-exercises"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.1.0"
libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"

resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.7")

scalacOptions += "-Ypartial-unification"

