import sbt._

class Project(info:ProjectInfo) extends DefaultProject(info) with IdeaProject {

  val casbah = "com.novus" % "casbah_2.8.0" % "1.0.8.5"   
  val bumnetworksRepo = "bumnetworks Repo" at "http://repo.bumnetworks.com/releases/"  

}

// vim: set ts=4 sw=4 et:
