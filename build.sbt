name := """de.htwg.mps.portals.play"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.scala-lang" % "scala-reflect" % "2.10.0",
  "com.escalatesoft.subcut" % "subcut_2.10" % "2.1"
)
