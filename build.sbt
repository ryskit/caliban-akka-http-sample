ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val CalibanVersion       = "1.4.0"
val CirceVersion         = "0.12.3"
val AkkaVersion          = "2.6.18"
val AkkaHttpCirceVersion = "1.39.2"
val TapirVersion         = "0.20.0"

lazy val root = (project in file("."))
  .aggregate(server)
  .settings(
    name := "caliban-akka-http-sample"
  )

lazy val server = (project in file("server"))
  .settings(
    name := "server",
    libraryDependencies ++= Seq(
      "com.typesafe.akka"           %% "akka-actor-typed"  % AkkaVersion,
      "com.github.ghostdogpr"       %% "caliban"           % CalibanVersion,
      "com.github.ghostdogpr"       %% "caliban-akka-http" % CalibanVersion,
      "com.github.ghostdogpr"       %% "caliban-tapir"     % CalibanVersion,
      "com.github.ghostdogpr"       %% "caliban-tools"     % CalibanVersion,
      "com.softwaremill.sttp.tapir" %% "tapir-json-circe"  % TapirVersion,
      "de.heikoseeberger"           %% "akka-http-circe"   % AkkaHttpCirceVersion
    )
  )
