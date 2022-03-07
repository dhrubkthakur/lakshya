name := "lakshya"

version := "0.1"

scalaVersion := "2.12.12"

lazy val global = project
  .in(file("."))
  .settings(scalaVersion := "2.12.12",
            settings,
            libraryDependencies ++= commonDependencies)
  .disablePlugins(AssemblyPlugin)

lazy val dependencies =
  new {
    val scalaTestVersion = "3.1.1"

    val log4j = "log4j" % "log4j" % "1.2.17"
    val scalaTest = "org.scalatest" %% "scalatest" % scalaTestVersion
    val typesafe = "com.typesafe" % "config" % "1.2.0"
    val httpClient = "org.apache.httpcomponents" % "httpclient" % "4.5.13"
  }

lazy val commonDependencies = Seq(
  dependencies.log4j % "provided",
  dependencies.typesafe,
  dependencies.scalaTest % "test",
  dependencies.httpClient
)

lazy val settings =
  commonSettings ++
    wartremoverSettings ++
    scalafmtSettings

lazy val compilerOptions = Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8"
)

lazy val commonSettings = Seq(
  scalacOptions ++= compilerOptions,
  resolvers ++= Seq(
    "Local Maven Repository" at "file://" + Path.userHome.absolutePath + "/.m2/repository",
    Resolver.sonatypeRepo("releases"),
    Resolver.sonatypeRepo("snapshots")
  )
)

scalaVersion in ThisBuild := "2.12.12"
parallelExecution in ThisBuild := false
concurrentRestrictions in Global += Tags.limit(Tags.Test, 1)

lazy val wartremoverSettings = Seq(
  wartremoverWarnings in (Compile, compile) ++= Warts.allBut(Wart.Throw)
)

lazy val scalafmtSettings =
  Seq(
    scalafmtOnCompile := true,
    scalafmtTestOnCompile := true,
    scalafmtVersion := "1.2.0"
  )

lazy val assemblySettings = Seq(
  assemblyJarName in assembly := "lakshya.jar",
  assemblyMergeStrategy in assembly := {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case "application.conf"            => MergeStrategy.concat
    case x                             => MergeStrategy.first
  }
)
