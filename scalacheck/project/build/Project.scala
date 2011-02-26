import sbt._

class MyProject(info:ProjectInfo) extends DefaultProject(info) {
  val scalaToolsReleases = ScalaToolsReleases    // http://scala-tools.org/repo-release/
  val scalacheck = "org.scalacheck" % "scalacheck" % "1.5"  

}

