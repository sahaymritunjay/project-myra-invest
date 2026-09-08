scalaVersion := "2.13.18"

lazy val root = rootProject
  .settings(
    name := "project-myra-invest",
    idePackagePrefix := Some("com.myra.invest"),
    libraryDependencies ++= Seq(
      // Source: https://mvnrepository.com/artifact/org.apache.spark/spark-sql
      "org.apache.spark" %% "spark-sql" % "3.5.6",
      // Source: https://mvnrepository.com/artifact/org.apache.spark/spark-core
      "org.apache.spark" %% "spark-core" % "3.5.6",
      // JSON Parsing
      "io.circe" %% "circe-core" % "0.14.15",
      "io.circe" %% "circe-parser" % "0.14.15",
      // Delta Lake
      "io.delta" %% "delta-spark" % "3.2.1"

    ),
    run / javaOptions += "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
    ThisBuild / javaOptions ++= Seq(
      "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED",
      "--add-opens=java.base/java.nio=ALL-UNNAMED"
    )
  )

