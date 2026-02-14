package org.theta

/**
 * bird(X) => can-fly(X) & animal(X)
 */
class Rule(relation:String, arguments:Map[String, Atom]) extends Fact(relation, arguments) {

  val statements:List[Fact] = Nil

  override def evaluate(binding: Binding, queryable: Queryable): Unit = {

    super.evaluate(binding, queryable)
  }

}
