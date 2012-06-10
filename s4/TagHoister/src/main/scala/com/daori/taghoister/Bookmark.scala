package com.daori.taghoister

import java.util.Date
case class Bookmark(val url: String, val user:String, created_at:String, title:String, tags:List[String], id:String) {
  
  def tagCombination = {

    def tagCom( targetTags:List[String]):List[(String,String)] = {
      targetTags match {
        case Nil => Nil
        case List(val1) => Nil
        case List(val1, val2) => List((val1, val2))
        case _ =>
          {
            val head = targetTags.head
            val tails = targetTags.tail

            var result:List[(String,String)]= Nil
            
            for( tailTag <- tails) {
              result = (head, tailTag) :: result
            }
            
            result ++ tagCom( tails)
          }
          
      }
    }
    
    tagCom( tags.sorted).toSet
  }
}

case class BookmarkContainer(bookmarks:List[Bookmark]) {
  
}