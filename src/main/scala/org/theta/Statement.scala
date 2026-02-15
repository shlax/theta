package org.theta

case class Statement(override val relation:String,
                     parameters:Map[String, Atom]) extends Term{

  override def arguments: Set[String] = parameters.keySet

  def matches(t:Term):Boolean = {
    t.relation == relation && t.arguments == arguments
  }

  override def evaluate(binding: Binding)(callback : => Unit): Unit = {
    for(candidate <- binding.query(matches)){
      binding.push{
        candidate.evaluate(binding)(callback)
      }
    }
  }

}