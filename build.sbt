import dependencies._

lazy val commonSettings = Seq(
  scalacOptions ++= commonScalacOptions,
  scalaVersion := "2.12.11",
  organization := "org.ergoplatform",
  version := "0.0.1",
  resolvers += Resolver.sonatypeRepo("public"),
  resolvers += Resolver.sonatypeRepo("snapshots"),
  test in assembly := {},
  assemblyMergeStrategy in assembly := {
    case "logback.xml"                                => MergeStrategy.first
    case "module-info.class"                          => MergeStrategy.discard
    case other if other.contains("io.netty.versions") => MergeStrategy.first
    case other                                        => (assemblyMergeStrategy in assembly).value(other)
  },
  libraryDependencies ++= CompilerPlugins
)

lazy val commonScalacOptions = List(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-feature",
  "-unchecked",
  "-Xfuture",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ypartial-unification"
)

lazy val allConfigDependency = "compile->compile;test->test"

lazy val dexBackend = project
  .in(file("."))
  .withId("ergo-dex-backend")
  .settings(commonSettings)
  .settings(moduleName := "ergo-dex-backend", name := "ErgoDexBackend")
  .aggregate(core, watcher, matcher, executor)

lazy val core = utils
  .mkModule("dex-core", "DexCore")
  .settings(commonSettings)
  .settings(libraryDependencies ++= dependencies.core ++ dependencies.Testing)

lazy val watcher = utils
  .mkModule("dex-watcher", "DexWatcher")
  .settings(commonSettings)
  .settings(
    mainClass in assembly := Some(
      "org.ergoplatform.explorer.http.api.Application"
    ),
    libraryDependencies ++= dependencies.watcher
  )
  .dependsOn(core)

lazy val matcher = utils
  .mkModule("dex-matcher", "DexMatcher")
  .settings(commonSettings)
  .settings(
    mainClass in assembly := Some(
      "org.ergoplatform.explorer.grabber.Application"
    ),
    libraryDependencies ++= dependencies.matcher
  )
  .dependsOn(core)

lazy val executor = utils
  .mkModule("dex-executor", "DexExecutor")
  .settings(commonSettings)
  .settings(
    mainClass in assembly := Some(
      "org.ergoplatform.explorer.grabber.Application"
    ),
    libraryDependencies ++= dependencies.executor
  )
  .dependsOn(core)