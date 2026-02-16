package org.theta.core

import org.theta.solver.Binding

class NotEqualClause extends Operator("!=", "x", "y"){

  override def evaluate(binding: Binding)(callback: => Unit): Unit = {
    val x = binding("x"); val y = binding("y")
    if(x.isDefined && y.isDefined){
      if(x.resolve != y.resolve){
        callback
      }
    }
  }

}
