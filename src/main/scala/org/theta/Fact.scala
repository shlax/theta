package org.theta

/**
 * capital(country:austria, city:vienna)
 */
case class Fact(override val relation:String, arguments:Map[String, Value]) extends Term{
  
  /** check if signature matches binding */
  def evaluate(binding: Binding)(callback : => Unit): Unit = {
    binding.push {
      if (arguments.forall { (key, atom) => binding.merge(key, atom) } ) {
        callback
      }
    }
  }

}
