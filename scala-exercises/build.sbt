name := "scala-exercises"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "org.typelevel" %% "cats-core" % "1.1.0"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.5" % "test"

libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.0" % "test"

libraryDependencies += "com.fortysevendeg" %% "scalacheck-datetime" % "0.2.0" % "test"

resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.7")

scalacOptions += "-Ypartial-unification"

