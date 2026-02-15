package org.theta

/**
 * capital(country:austria, city:vienna)
 */
case class Fact(override val relation:String,
                parameters:Map[String, Value]) extends Clause{
  
  override def arguments: Set[String] = parameters.keySet

  /** check if signature matches binding */
  def evaluate(binding: Binding)(callback : => Unit): Unit = {
    binding.push {
      if (parameters.forall { (key, atom) => binding.merge(key, atom) } ) {
        callback
      }
    }
  }

}
