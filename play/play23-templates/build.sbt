name := """play23-templates"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

//swagger
resolvers += Resolver.bintrayRepo("markusjura", "maven")
libraryDependencies += "com.markusjura" %% "swagger-play2" % "1.3.7"

libraryDependencies ++= Seq(
    jdbc,
    anorm,
    cache,
    ws
)


