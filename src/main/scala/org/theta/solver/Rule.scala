package org.theta.solver

/**
 * bird(x) => can-fly(x) & animal(x)
 */
case class Rule(override val relation:String,
                parameters:Map[String, Atom],
                statements:Iterable[Statement] = Nil) extends Clause {

  override def arguments: Set[String] = parameters.keySet

  def evaluate(binding: Binding, stack:Iterable[Statement])(callback : => Unit): Unit = {
    stack match {
      case Nil =>
        callback
      case head :: tail =>
        val context = head.parameters.map { (k, v) =>
          v match {
            case Value(v) => k -> Variable(v)
            case Reference(nm) => k -> binding(nm)
          }
        }
        head.evaluate( Binding(context, binding) ){
          evaluate(binding, tail)(callback)
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
        for( s <- statements; (k, v) <- s.parameters){
          v match {
            case Reference(nm) if !variables.contains(nm) =>
              variables = variables + (nm -> Variable())
            case _ =>
          }

        }
        evaluate( Binding(variables, binding), statements )(callback)
      }
    }
  }

}
