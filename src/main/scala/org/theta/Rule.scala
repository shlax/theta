package org.theta

/**
 * bird(x) => can-fly(x) & animal(x)
 */
class Rule(val relation:String, val arguments:Map[String, Atom]) extends Term {

  val statements:List[Rule] = Nil

  override def evaluate(binding: Binding): Unit = {


  }

}
