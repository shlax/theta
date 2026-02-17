package org.theta.solver

case class Statement(override val relation:String,
                     parameters:Map[String, Atom]) extends Term{

  override def arguments: Set[String] = parameters.keySet

  def matches(t:Term):Boolean = {
    t.relation == relation && t.arguments == arguments
  }

  override def evaluate(binding: Binding)(callback : => Unit): Unit = {
    val context = parameters.map { (k, v) =>
      v match {
        case Value(x) => k -> Variable(x)
        case Reference(nm) => k -> binding(nm)
      }
    }
    val statementBinding = Binding(context, binding)

    for(candidate <- binding.query(matches)){
      candidate.evaluate(statementBinding)(callback)
    }

  }

}