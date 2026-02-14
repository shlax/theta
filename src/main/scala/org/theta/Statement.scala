package org.theta

case class Statement(relation:String, arguments:Map[String, Atom]) {

  def matches(t:Term):Boolean = {
    t.relation == relation && t.arguments == arguments.keySet
  }

}