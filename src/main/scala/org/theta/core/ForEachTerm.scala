package org.theta.core

import org.theta.solver.{Binding, Term}

class ForEachTerm(val terms:Iterable[Term]) extends Term{

  override def evaluate(binding: Binding)(callback: => Boolean): Boolean = {
    var continue = true
    for(t <- terms if continue){
      continue = t.evaluate(binding)(callback)
    }
    continue
  }

}
