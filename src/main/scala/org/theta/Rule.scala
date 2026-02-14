package org.theta

/**
 * bird(x) => can-fly(x) & animal(x)
 */
class Rule(override val relation:String, val arguments:Map[String, Atom]) extends Term {

  val statements:List[Rule] = Nil

  def evaluate(binding: Binding, stack:List[Rule])(callback : => Unit): Unit = {
    stack match {
      case Nil =>
        callback
      case head :: tail =>
        for(candidate <- binding.query(_.relation == head.relation) ){
          binding.push {
            val context = head.arguments.map{ (k, v) =>
              v match {
                case Value(v) => k -> Variable(v)
                case Reference => k -> binding(k)
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
    val values = arguments.flatMap { (k, v) =>
      v match {
        case x : Value => Some(k -> x)
        case _ => None
      }
    }

    binding.push {
      // check if signature matches binding
      if( values.forall { (key, atom) => binding.merge(key, atom) } ){
        var variables = arguments.flatMap{ (k, v) =>
          v match {
            case Reference => Some(k -> binding(k))
            case _ => None
          }
        }
        for( s <- statements; (k, v) <- s.arguments if v == Reference && !variables.contains(k) ){
          variables = variables + (k -> Variable())
        }
        val context = Binding(variables, binding)
        evaluate(context, statements)(callback)
      }
    }
  }

}
