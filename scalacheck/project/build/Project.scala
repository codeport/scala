import sbt._

class MyProject(info:ProjectInfo) extends DefaultProject(info) {
  val scalaToolsReleases = ScalaToolsReleases    // http://scala-tools.org/repo-release/
  val scalacheck = "org.scala-tools.testing" % "scalacheck_2.8.1" % "1.8"

}

