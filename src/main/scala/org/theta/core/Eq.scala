package org.theta.core

import org.theta.Binding

class Eq extends Operator("==", "x", "y"){

  override def evaluate(binding: Binding)(callback: => Unit): Unit = {
    val x = binding("x")
    val y = binding("y")
    if(x.value.isDefined && y.value.isDefined){
      if(x.value.get == y.value.get){
        callback
      }
    }
  }

}
