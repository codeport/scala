import sbt._

class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
    val mvnTwitter = "mvn.twitter" at "http://maven.twttr.com/"
    val defaultProject = "com.twitter" % "standard-project" % "0.11.33-NEST"
}

// vim: set ts=4 sw=4 et:
