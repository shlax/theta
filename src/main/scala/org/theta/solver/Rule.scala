package org.theta.solver

/**
 * bird(x) => can-fly(x) & animal(x)
 */
case class Rule(override val relation:String,
                parameters:Map[String, Atom],
                statements:Iterable[Statement] = Nil) extends Clause {

  parameters.values.collect{
      case Reference(nm) => nm
  }.groupBy(identity).filter(_._2.size > 1).foreach{ (nm, _) =>
    throw new IllegalArgumentException(s"Duplicate variable name: $nm")
  }

  override def arguments: Set[String] = parameters.keySet

  def evaluate(binding: Binding, stack:Iterable[Statement])(callback : => Unit): Unit = {
    stack match {
      case Nil =>
        callback
      case head :: tail =>
        head.evaluate( binding ){
          evaluate(binding, tail)(callback)
        }
    }
  }

  override def evaluate(binding: Binding)(callback : => Unit): Unit = {
    val values = parameters.collect {
      case (k, x : Value) => k -> x
    }

    binding.push {
      // check if signature matches binding
      if( values.forall { (key, atom) => binding.merge(key, atom) } ){
        var variables = parameters.collect{
          case (k, Reference(nm) ) => nm -> binding(k)
        }
        for( s <- statements; (k, v) <- s.parameters if v.isInstanceOf[Reference]){
          val nm = v.asInstanceOf[Reference].name
          if (!variables.contains(nm)) {
            variables = variables + (nm -> Variable())
          }
        }
        evaluate( Binding(variables, binding), statements )(callback)
      }
    }
  }

}
