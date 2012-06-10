package com.daori.taghoister

import net.liftweb.json.JsonParser
import java.io.BufferedReader
import net.liftweb.json.DefaultFormats
import java.io.FileReader

class TagHoister {
  val mmap = new LasMultiMap[(String, String), Bookmark]()

  implicit val formats = DefaultFormats

  val reader = new BufferedReader(new FileReader("diigo-bookmark.1000.type1.json"))
  val data = new StringBuilder
  var line = reader.readLine

  while (line != null) {
    data append line
    line = reader.readLine
  }
  val jsons = JsonParser.parse(data.toString)

  val bookmarks = jsons.extract[List[Bookmark]]

  for (bookmark <- bookmarks) {
    for (tagCombination <- bookmark.tagCombination) {
      mmap.put(tagCombination, bookmark)
    }
  }
}
