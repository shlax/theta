package org.theta

/**
 * bird(x) => can-fly(x) & animal(x)
 */
case class Rule(override val relation:String,
                parameters:Map[String, Atom],
                statements:List[Statement]) extends Term {

  override def arguments: Set[String] = parameters.keySet

  def evaluate(binding: Binding, stack:List[Statement])(callback : => Unit): Unit = {
    stack match {
      case Nil =>
        callback
      case head :: tail =>
        for(candidate <- binding.query(head.matches) ){
          binding.push {
            val context = head.arguments.map{ (k, v) =>
              v match {
                case Value(v) => k -> Variable(v)
                case Reference(nm) => k -> binding(nm)
              }
            }
            val statementBinding = Binding(context, binding)
            candidate.evaluate(statementBinding){
              evaluate(binding, tail)(callback)
            }
          }
        }
    }
  }

  override def evaluate(binding: Binding)(callback : => Unit): Unit = {
    val values = parameters.flatMap { (k, v) =>
      v match {
        case x : Value => Some(k -> x)
        case _ => None
      }
    }

    binding.push {
      // check if signature matches binding
      if( values.forall { (key, atom) => binding.merge(key, atom) } ){
        var variables = parameters.flatMap{ (k, v) =>
          v match {
            case Reference(nm) => Some(nm -> binding(k))
            case _ => None
          }
        }
        for( s <- statements; (k, v) <- s.arguments){
          v match {
            case Reference(nm) if !variables.contains(nm) =>
              variables = variables + (nm -> Variable())
            case _ =>
          }

        }
        val context = Binding(variables, binding)
        evaluate(context, statements)(callback)
      }
    }
  }

}
