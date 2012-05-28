package pis.chap23

import java.io.File
import com.drew.metadata.Metadata
import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.Directory
import com.drew.metadata.Tag
import scala.collection.JavaConversions._
import com.drew.metadata.exif.GpsDirectory
import com.sun.jdi.FloatValue

class ExifInfo(var path: String) {
  var jpegFile = new File(path);
  var metadata = ImageMetadataReader.readMetadata(jpegFile);

  def map[T](f: ExifDirectory => T) = {
    var result = List[T]()

    for (dir <- metadata.getDirectories) {
      result = f( new ExifDirectory(dir)) :: result
    }
    result
  }

  def foreach(b: ExifDirectory => Unit) {
    for (dir <- metadata.getDirectories) {
      b(new ExifDirectory(dir))
    }
  }

  def withFilter(p: ExifDirectory => Boolean) = {
    var result = List[ExifDirectory]()
    for (dir <- metadata.getDirectories) {
      val exifDir = new ExifDirectory(dir) 
      if (p(exifDir)) {
        result = exifDir :: result
      }
    }

    result
  }

  class ExifTag(val tag: Tag) {
	  override def toString() = tag.toString
  }

  class ExifDirectory(val dir: Directory) {
    def map[T](f: ExifTag => T) = {
      var result = List[T]()

      for (tag <- dir.getTags) {
        result = f(new ExifTag(tag)) :: result
      }
      result
    }
    
    def name = dir.getName
    
    def tags = dir.getTags

    def foreach(b: ExifTag => Unit) {
      for (tag <- dir.getTags) {
        b( new ExifTag(tag))
      }
    }
    
    def latitude = {
      val d = dir.getRationalArray(GpsDirectory.TAG_GPS_LATITUDE)(0).floatValue
      val m = dir.getRationalArray(GpsDirectory.TAG_GPS_LATITUDE)(1).floatValue/60
	  val s = dir.getRationalArray(GpsDirectory.TAG_GPS_LATITUDE)(2).floatValue/60/60
	  d + m + s
    }
    
    def longitude = {
      val d = dir.getRationalArray(GpsDirectory.TAG_GPS_LONGITUDE)(0).floatValue
	  val m = dir.getRationalArray(GpsDirectory.TAG_GPS_LONGITUDE)(1).floatValue/60
	  val s = dir.getRationalArray(GpsDirectory.TAG_GPS_LONGITUDE)(2).floatValue/60/60
	  d + m + s
    }
    
    override def toString() = path+",camera,"+this.latitude+","+this.longitude
  }
}