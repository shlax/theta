package org.theta

/**
 * bird(x) => can-fly(x) & animal(x)
 */
class Rule(val relation:String, val arguments:Map[String, Atom]) extends Term {

  val statements:List[Rule] = Nil

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
            case Reference => Some(k -> binding.state(k))
            case _ => None
          }
        }
        for( s <- statements; (k, v) <- s.arguments if v == Reference && !variables.contains(k) ){
          variables = variables + (k -> Variable())
        }
        val context = Binding(variables, binding)

      }
    }
  }

}
