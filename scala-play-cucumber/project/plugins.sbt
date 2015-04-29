logLevel := Level.Warn

resolvers += Resolver.file("Local repo", file(System.getProperty("user.home") + "/.ivy2/local"))(Resolver.ivyStylePatterns)

resolvers += "Sonatype-Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.3.8")

resolvers += "Templemore Repository" at "http://templemore.co.uk/repo"

addSbtPlugin("templemore" % "sbt-cucumber-plugin" % "0.8.0")

