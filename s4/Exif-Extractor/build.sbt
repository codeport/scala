name := "Exif-Extractor"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies += "org.scalatest" % "scalatest_2.9.1" % "1.6.1" % "test"

libraryDependencies += "junit" % "junit" % "4.8.1" % "test"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "6.0.4"

libraryDependencies += "metadata-extractor" % "metadata-extractor" % "2.6.1" from "file://lib/metadata-extractor-2.6.1.jar"
