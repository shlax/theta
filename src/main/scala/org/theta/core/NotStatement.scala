package org.theta.core

import org.theta.solver.{Binding, Statement, Term}

class NotStatement(val statement: Statement) extends Statement{

  override def arguments: Set[String] = statement.arguments

  override def variables: Set[String] = statement.variables

  override def evaluate(binding: Binding)(callback: => Boolean): Boolean = {
    var called = false
    val continue = statement.evaluate(binding){
      called = true
      false
    }
    if(!called){
      callback
    }else true
  }

}
