import _root_.sbt.Keys._
import _root_.sbt.Keys._

name := "func-prog-in-scala"

scalaVersion := "2.11.2"

version := "1.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.1.5" % "test"

libraryDependencies += "junit" % "junit" % "4.10" % "test"