scalaVersion := "2.13.18"

lazy val root = rootProject
  .settings(
    name := "project-myra-invest",
    idePackagePrefix := Some("com.myra.invest"),
    libraryDependencies ++= Seq(

      "org.apache.spark" %% "spark-sql" % "3.5.6",
      "org.apache.spark" %% "spark-core" % "3.5.6",

      "io.circe" %% "circe-core" % "0.14.15",
      "io.circe" %% "circe-parser" % "0.14.15",

      // Delta Lake
      "io.delta" %% "delta-spark" % "3.2.1",
      "com.typesafe" % "config" % "1.4.3",

      // JSON parsing
      "org.json4s" %% "json4s-native" % "4.0.7",
      "org.json4s" %% "json4s-jackson" % "4.0.7"

    ),
    run / javaOptions += "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
    ThisBuild / javaOptions ++= Seq(
      "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED",
      "--add-opens=java.base/java.nio=ALL-UNNAMED"
    )
  )

