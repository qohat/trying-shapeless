ThisBuild / organization := "qohat"
ThisBuild / scalaVersion := "2.13.7"
ThisBuild / version := "0.0.1"
ThisBuild / scalafmtOnCompile := true

lazy val root = (project in file("."))
  .settings(Defaults.itSettings)
  .settings(
    name := "gh-alerts-subscriptions-scala",
    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots")
    ),
    libraryDependencies ++= Seq(
      "com.chuusai"    %% "shapeless"  % "2.3.3",
      "org.scalameta"  %% "munit"      % "0.7.27" % "test,it",
      "org.scalacheck" %% "scalacheck" % "1.15.4" % Test,
      "org.scalatest"  %% "scalatest"  % "3.2.10"
    ),
    addCompilerPlugin(
      "org.typelevel" %% "kind-projector" % "0.13.2" cross CrossVersion.full
    ),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1"),
    testFrameworks += new TestFramework("munit.Framework"),
    Test / fork := true,
    scalacOptions += "-Ymacro-annotations"
  )
