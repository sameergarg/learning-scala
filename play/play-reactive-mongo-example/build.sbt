name := """play-reactive-mongo-example"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Typesafe Snapshots repository" at "http://repo.typesafe.com/typesafe/snapshots/"

resolvers += "Sonatype OSS Releases" at "https://oss.sonatype.org/content/repositories/releases"

resolvers += "Sonatype OSS Snapshot Releases" at "https://oss.sonatype.org/content/repositories/snapshots"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  //"org.reactivemongo" %% "reactivemongo" % "0.10.0"
  "org.reactivemongo" %% "play2-reactivemongo" % "0.11.0-SNAPSHOT",
  "ch.qos.logback" % "logback-classic" % "1.0.13"
)

//test
libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "2.2.0" % "test",
  "com.github.simplyscala" % "scalatest-embedmongo_2.11" % "0.2.2-SNAPSHOT" % "test"
)