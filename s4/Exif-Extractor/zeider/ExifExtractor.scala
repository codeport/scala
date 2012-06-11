package chap23

import sys.process._
import java.io.File
import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Metadata
import com.drew.metadata.exif.GpsDirectory
import com.drew.metadata.exif.GpsDescriptor
import com.drew.metadata.Directory
import scala.collection.JavaConversions._

class ExifExtractor {

	//read file
	def readFile(path:String) = {
		("find " + path + " -type f -name *.JPG -or -name *.jpg").lines_!
	}
	
	//get exif
	def getExif(files:Stream[String]):Stream[Metadata] = {
		files map (f => ImageMetadataReader.readMetadata(new File(f)))
	}
	
	//get directory
	def getDirectory(metadatas:Stream[Metadata]):List[(String, String)] = {
		for(data <- metadatas) {
			getTags(data.getDirectories())
		} 
		
		var list = List[(String, String)]();
		
		for(data <- metadatas) {
			list = getDescription(data) :: list
		}
		
		list
	}
	
	def getTags(directories:Iterable[Directory]) = {
		directories flatMap (directory => getTag(directory))
	}
	
	def getTag(directory:Directory) = {
		directory.getTags() withFilter(tag => tag.getTagName() startsWith "GPS" ) map (tag => println(tag))
	}
	
	def getDescription(metadata:Metadata):(String, String) = {
		val dir = metadata.getDirectory(classOf[GpsDirectory])
		val descriptor = new GpsDescriptor(dir)
		
		(descriptor.getGpsLatitudeDescription(), descriptor.getGpsLongitudeDescription())
	}
	
	
}