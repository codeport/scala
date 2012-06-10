package com.daori.taghoister

import java.io.BufferedReader
import java.io.FileReader
import net.liftweb.json.JsonParser
import net.liftweb.json.DefaultFormats
import scala.collection.immutable.Set
import java.io.File
import java.io.FileWriter

object Main extends App {
  
  def printAll(m: TagHoister):String = {
    val sb: StringBuilder = new StringBuilder
    for (r <- m.mmap.map) {
      if (r._2.size >= 2) sb.append(decorate(r._1, r._2))
    }
    return sb.toString
  }

  def print(m: TagHoister, keys: Array[String]):String = {
    val sortedKeys = keys.sorted
    decorate((sortedKeys(0), sortedKeys(1)), m.mmap((sortedKeys(0), sortedKeys(1))).get)
  }
  
  def decorate(tags:(String, String), list:List[Bookmark]):String = {
    val sb: StringBuilder = new StringBuilder
    sb.append("<ul>")
    for (bookmark <- list) {
      sb.append("<li><a href='" + bookmark.url + "'>" + bookmark.title + "</a></li>")
    }
    sb.append("</ul>")
    sb.append("<div>" + tags._1 + ", " + tags._2 + "</div>")
    sb.append("</div><br/>")
    return sb.toString
  }

  def tags(m: TagHoister) = {
    m.mmap.keySet
  }

  override def main(args: Array[String]) = {
    val m: TagHoister = new TagHoister
    var result = ""
    if (args.length == 0) result = printAll(m)
    else result = print(m, args)
    var file = new File("output.html")
    var fileW = new FileWriter(file)
    fileW.write(result)
    fileW.close
  }
}