package org.theta.solver

case class RuleStatement(relation:String,
                         parameters:Map[String, Atom]) extends Statement{

  override def arguments: Set[String] = parameters.keySet

  override def variables: Set[String] = parameters.values.collect{
    case Reference(nm) => nm
  }.toSet

  def matches(t:Clause):Boolean = {
    t.relation == relation && t.arguments == arguments
  }

  override def evaluate(binding: Binding)(callback : => Boolean): Boolean = {
    val context = parameters.map { (k, v) =>
      v match {
        case Value(x) => k -> Variable(x)
        case Reference(nm) => k -> binding(nm)
      }
    }
    val statementBinding = Binding(context, binding)

    var continue = true
    for(candidate <- binding.query(matches) if continue){
      continue = candidate.evaluate(statementBinding)(callback)
    }
    continue
  }

}