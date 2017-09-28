name := "free-monad-tagless"

version := "0.1"

scalaVersion := "2.12.3"


val catsVersion = "1.0.0-MF"

libraryDependencies := Seq(
  "org.typelevel" %% "cats-core" % catsVersion,
  "org.typelevel" %% "cats-free" % catsVersion
)
        