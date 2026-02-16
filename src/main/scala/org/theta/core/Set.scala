package org.theta.core

import org.theta.Binding

class Set extends Operator("=", "x", "y"){

  override def evaluate(binding: Binding)(callback: => Unit): Unit = {
    val x = binding("x")
    val y = binding("y")
    if(x.value.isDefined && y.value.isDefined){
      if(x.value.get == y.value.get){
        callback
      }
    }else if(x.value.isDefined && y.value.isEmpty){
      binding.push{
        y.value = x.value
        callback
      }
    }else if(x.value.isEmpty && y.value.isDefined){
      binding.push {
        x.value = y.value
        callback
      }
    }
  }

}
