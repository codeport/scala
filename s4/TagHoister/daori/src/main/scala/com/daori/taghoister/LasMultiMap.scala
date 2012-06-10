package com.daori.taghoister

import scala.collection.mutable.HashMap

class LasMultiMap[K, V] {

  var map:HashMap[K, List[V]] = new HashMap[K, List[V]]
  
  def update(key: K, value: V) {
    if (map.contains(key)) {
      map.put(key, map.get(key).get :+ value)
    } else {
      map.put(key, value :: Nil)
    }
  }
  
  def apply(key: K): Option[List[V]] = {
    map.get(key)
  }
  
  def put(key:K, value:V) = {
    update(key, value)
  }
  
  def keySet():scala.collection.Set[K] = {
    map.keySet
  }
}