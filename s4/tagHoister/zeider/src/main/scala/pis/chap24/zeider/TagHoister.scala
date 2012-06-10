package pis.chap24.zeider

import java.net.URL
import scala.io.Source
import scala.util.parsing.json._

class TagHoister {
  def extractAllTags(bookmarks:List[Bookmark]) = {
    println("extractAllTags")
	bookmarks flatMap (bookmark => bookmark.tags)
  }
  def removeDuplicatedTags(bookmarks:List[Bookmark]) = {
    println("removeDuplicatedTags")
    bookmarks flatMap (x => x.tags) toSet
  }
  def findTagsTwoMoreUsed(bookmarks:List[Bookmark]) = {
	println("findTagsTwoMoreUsed")
    val tags = extractAllTags(bookmarks)
	val uniqueTags = removeDuplicatedTags(bookmarks)
	println(uniqueTags.size)
    uniqueTags map (x => tags filter (y => y == x)) filter (z => z.length > 1) flatMap ( m => m.toSet )
  }
  def findCandidateTagGroup(bookmarks:List[Bookmark]) = {
	println("findCandidateTagGroup")
    val tags = findTagsTwoMoreUsed(bookmarks)
    tags flatMap (x => Set(x) zipAll(tags, x, null)) map (y => Set(y._1, y._2)) filter (z => z.size > 1) toList
  }
  def findNotTheSameButSimilarGroup(bookmarks:List[Bookmark]) = {
	println("findNotTheSameButSimilarGroup")
    val tagGroup = findCandidateTagGroup(bookmarks)
    (tagGroup.view).par flatMap (x => bookmarks map ( y => (x & y.tags.toSet, y)) 
    		filter (z => z._1.size > 1)) groupBy (m => m._1) map (l => Map(l._1 -> (l._2 map (n => n._2)).toSet)) flatMap (b => b) filter (c => c._2.size > 1) toMap
  }
  
  def convert(jsonData:String):List[Bookmark] = {
        
        JSON.parseRaw(jsonData) match {
            
            case jsonObject:Some[JSONObject] => {
                
                jsonObject.get.obj.get("results") match {
                    case jsonArray:Some[JSONArray] => {
                            jsonArray.get.list.map(value => {
                                val jsonBookmark = value.asInstanceOf[JSONObject].obj;
                                
                                val id = jsonBookmark("id").toString()
                                val user = jsonBookmark("user").toString()
                                val title = jsonBookmark("title").toString()
                                val url = jsonBookmark("url").toString()
                                val tags = jsonBookmark("tags").asInstanceOf[JSONArray]
                                                               .list.map(obj => obj.toString())
                                
                                Bookmark(id, user, title, url, tags)
                            })
                        }
                    case None => Nil
                }
                
            }
            case None => Nil
        }
    }
}

object TagHoister extends App {
  println("started")
  val url = new URL("https://raw.github.com/gist/2900472/4ff97a0c416d37a14f4224b24895b0224c3e335c/bookmark.json")
  println("get Url")
  val jsonData = Source.fromURL(url).mkString
  var th = new TagHoister
  println("convert")
  val bookmarks = th convert jsonData
  println("solving... plz wait!")
  val result = th findNotTheSameButSimilarGroup bookmarks
  result map (x => {
    println("> Tags [" + (x._1 map (y => y)) + "]")
    x._2 map (bookmark => println("- " + bookmark))
    println("-----------")
  })
}

case class Bookmark(id:String, user:String, title:String, url:String, tags:List[String]) {
  override def toString() = {
    url + "/" + tags
  }
}