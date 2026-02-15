package org.theta

case class Statement(relation:String, arguments:Map[String, Atom]) {

  def matches(t:Term):Boolean = {
    t.relation == relation && t.arguments == arguments.keySet
  }

  def evaluate(binding: Binding)(callback : => Unit): Unit = {
    for(candidate <- binding.query(matches)){
      binding.push{
        candidate.evaluate(binding)(callback)
      }
    }
  }

}