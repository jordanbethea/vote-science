name := """vote-science"""
organization := "com.github.jordanbethea"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "com.typesafe.play" %% "play-slick" % "4.0.2"
libraryDependencies += "com.h2database" % "h2" % "1.4.200"
libraryDependencies += jdbc % Test

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.github.jordanbethea.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.github.jordanbethea.binders._"
