name := """conference-app"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.5"

libraryDependencies ++= Seq(ws, guice, specs2 % Test)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "5.0.0",
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc4",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.0.0",
  "com.h2database" % "h2" % "1.4.200",
  "org.webjars" %% "webjars-play" % "2.8.0-1",
  "org.webjars" % "bootstrap" % "3.1.1-2"
)

TwirlKeys.templateImports += "models._"

