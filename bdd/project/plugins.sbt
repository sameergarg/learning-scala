logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.8")

resolvers += "Templemore Repository" at "http://templemore.co.uk/repo"

addSbtPlugin("templemore" %% "sbt-cucumber-plugin" % "0.8.0" from "http://templemore.co.uk/repo/templemore/sbt-cucumber-plugin_2.10_0.13/0.8.0/sbt-cucumber-plugin-0.8.0.jar")