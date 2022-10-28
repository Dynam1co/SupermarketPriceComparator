ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.2"

lazy val root = (project in file("."))
  .settings(
    name := "PriceComparator"
  )

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.1.3"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.1.3"
libraryDependencies += "io.minio" % "minio" % "8.4.2"
libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "3.2.1"
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.3"
libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.13.3"
libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "3.2.1"
libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "3.2.1"
libraryDependencies += "com.typesafe" % "config" % "1.4.2"
libraryDependencies += "com.softwaremill.sttp.client3" %% "core" % "3.7.6"
libraryDependencies += "org.json4s" %% "json4s-native" % "3.6.0-M2"

// IMPORTANTE:
// A parte de tener bien la versión de winutils, hay que copiar el archivo hadoop.dll a la carpeta System32

// Dependent libraries
libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk" % "1.12.239" exclude("com.fasterxml.jackson.core", "jackson-databind"),
  "org.apache.commons" % "commons-csv" % "1.9.0",
  "org.slf4j" % "slf4j-api" % "1.7.36",
  "org.mockito" % "mockito-core" % "4.6.1"
)