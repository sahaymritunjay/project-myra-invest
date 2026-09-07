scalaVersion := "2.13.18"

lazy val root = rootProject
  .settings(
    name := "project-myra-invest",
    idePackagePrefix := Some("com.myra.invest"),
  )
libraryDependencies ++= Seq(
  // Source: https://mvnrepository.com/artifact/org.apache.spark/spark-sql
  "org.apache.spark" %% "spark-sql" % "4.2.0",
  // Source: https://mvnrepository.com/artifact/org.apache.spark/spark-core
  "org.apache.spark" %% "spark-core" % "4.2.0",
  "org.scalatest" %% "scalatest" % "3.2.20" % "test"
)
