package org.theta.core

import org.theta.solver.{Binding, Value}

class SetOperator extends Operator("=", "x", "y"){

  override def evaluate(binding: Binding)(callback: => Unit): Unit = {
    val x = binding("x")
    val y = binding("y")
    if(x.isDefined && y.isDefined){
      if(x.resolve == y.resolve){
        callback
      }
    }else if(x.isDefined && y.isEmpty){
      binding.push{
        if( binding.merge("y", Value(x.resolve)) ){
          callback
        }
      }
    }else if(x.isEmpty && y.isDefined){
      binding.push {
        if( binding.merge("x", Value(y.resolve)) ) {
          callback
        }
      }
    }
  }

}
