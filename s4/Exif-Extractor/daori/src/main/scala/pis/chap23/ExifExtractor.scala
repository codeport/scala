package pis.chap23

import java.io.File

import scala.collection.immutable.StreamIterator
import scala.sys.process.stringToProcess

object ExifExtractor extends App {
	override def main(args:Array[String]) = {
	  if (args.length != 1) {
	    println("scala " + this.getClass.getName + " <Image Directory>")
	  }
	  val directory = args(0)
      var iter = new StreamIterator("ls "+directory lines_!)
      println("name,symbol,latitude,longitude")
      while(iter.hasNext) {
        var filename = iter.next
    	var jpegFile = new ExifInfo(directory+File.separator+filename)
    	for (dir <- jpegFile; if dir.name.equals("GPS")) println(dir.toString)
      }
	}
}