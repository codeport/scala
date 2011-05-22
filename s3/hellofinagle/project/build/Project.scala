import sbt._
import com.twitter.sbt._

class Project(info: ProjectInfo) extends StandardServiceProject(info) {
    override def filterScalaJars = false

    // finagle
    val finagle =  "com.twitter" % "finagle" % "1.5.1"

    //test
    val scalatest   = "org.scalatest" % "scalatest" % "1.3"

    //repos
    val clojarsRepo  = "clojars" at "http://clojars.org/repo/" 
    val twitterRepo  = "mvn.twitter" at "http://maven.twttr.com/"
    val codehausRepo = "codehaus.org" at "http://repository.codehaus.org/"
    val akkaRepo     = "akka.io " at "http://akka.io/repository"
    val nettyRepo    = "repository.jboss.org" at "http://repository.jboss.org/nexus/content/groups/public/"
    val scalaToolsSnapshotsRepo = "snapshots" at "http://scala-tools.org/repo-snapshots"
    val scalaToolsReleasesRepo  = "releases" at "http://scala-tools.org/repo-releases"

    override def ivyXML =
      <dependencies>
        <exclude module="jms"/>
        <exclude module="jmxtools"/>
        <exclude module="jmxri"/>
      </dependencies>

}

// vim: set ts=4 sw=4 et:
