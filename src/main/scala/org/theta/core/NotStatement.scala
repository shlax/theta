package org.theta.core

import org.theta.solver.{Binding, Term}

object NotStatement {

  def not(t:Term): NotStatement = {
    NotStatement(t)
  }

}

class NotStatement(val term: Term) extends Term{

  override def relation: String = term.relation
  override def arguments: Set[String] = term.arguments

  override def evaluate(binding: Binding)(callback: => Boolean): Boolean = {
    var called = false
    val continue = term.evaluate(binding){
      called = true
      false
    }
    if(!called){
      callback
    }else true
  }
}
