ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "inyeccion-de-dependencias",
    libraryDependencies ++= Seq(
      "org.reactivemongo" %% "reactivemongo" % "1.0.3",
      "de.flapdoodle.embed" % "de.flapdoodle.embed.mongo" % "3.0.0" % Test,
      "com.typesafe.akka" % "akka-actor-typed_2.13" % "2.6.14",
      "com.typesafe.akka" % "akka-stream-typed_2.13" % "2.6.14",
      "com.typesafe.akka" % "akka-http_2.13" % "10.2.4",
      "com.typesafe.akka" % "akka-http-spray-json_2.13" % "10.2.4",
      "ch.megard" % "akka-http-cors_2.13" % "1.1.1",
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.9",
      "org.scalaz" %% "scalaz-core" % "7.3.6"
    )
  )
