import BuildSettings._

name := "spray-todo"

version := "1.0"

scalaVersion := "2.10.2"

resolvers ++= Seq(
  "spray nightlies" at "http://nightlies.spray.io",
  Resolver.sonatypeRepo("snapshots")
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.2.0",
  "com.typesafe.akka" %% "akka-slf4j" % "2.2.0",
  "io.argonaut" %% "argonaut" % "6.0",
  "io.spray" % "spray-can" % "1.2-20130801",
  "io.spray" % "spray-routing" % "1.2-20130801",
  "ch.qos.logback" % "logback-classic" % "1.0.13"
)

seq(sprayTodoSettings: _*)