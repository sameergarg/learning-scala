name := "functional-patterns"

version := "0.1"

scalaVersion := "2.12.4"
val catsVersion = "1.0.1"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "cats-free" % catsVersion,
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"
)

addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.4")

resolvers += Resolver.sonatypeRepo("releases")