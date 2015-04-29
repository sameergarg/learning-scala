name := "playCucumber"

version := "1.0"

resolvers += "Sonatype-Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

lazy val `playcucumber` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.10.4"

libraryDependencies ++= Seq( jdbc , anorm , cache , ws,
  "org.scalatest" % "scalatest_2.10" % "2.2.1" % "test",
  "info.cukes" % "cucumber-scala_2.10" % "1.2.2" % "test",
  "info.cukes" % "cucumber-junit" % "1.2.2" % "test"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

cucumberSettings

cucumberFeaturesLocation := "./test/features"

cucumberStepsBasePackage := "features.steps"

