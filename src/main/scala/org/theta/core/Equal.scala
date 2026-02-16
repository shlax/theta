package org.theta.core

import org.theta.solver.{Binding, Statement}

class Equal extends Operator("==", "x", "y"){

  def isEqual(x:Any, y:Any):Boolean = {
    x == y
  }

  override def evaluate(binding: Binding)(callback: => Unit): Unit = {
    val x = binding("x")
    val y = binding("y")
    if(x.value.isDefined && y.value.isDefined){
      if(isEqual(x.value.get, y.value.get)){
        callback
      }
    }
  }

}
