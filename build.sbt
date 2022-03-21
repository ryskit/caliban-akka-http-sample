ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val CalibanVersion       = "1.4.0"
val CirceVersion         = "0.12.3"
val AkkaVersion          = "2.6.18"
val AkkaHttpCirceVersion = "1.39.2"

lazy val root = (project in file("."))
  .settings(
    name := "caliban-akka-http-sample",
    libraryDependencies ++= Seq(
      "com.typesafe.akka"           %% "akka-actor-typed"  % AkkaVersion,
      "com.github.ghostdogpr"       %% "caliban"           % CalibanVersion,
      "com.github.ghostdogpr"       %% "caliban-akka-http" % CalibanVersion,
      "com.github.ghostdogpr"       %% "caliban-tapir"     % CalibanVersion,
      "com.github.ghostdogpr"       %% "caliban-tools"     % CalibanVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe"  % "0.20.0",
      "de.heikoseeberger"           %% "akka-http-circe"   % AkkaHttpCirceVersion
    )
  )
