package org.theta

/**
 * bird(X) => can-fly(X) & animal(X)
 */
class Rule(val relation:String, arguments:Map[String, Atom]) extends Term {

  val statements:List[Rule] = Nil

  override def evaluate(binding: Binding): Unit = {

  }

}
