package org.theta.core

import org.theta.solver.{Binding, Value}

class ListClause extends Operator("[|]", "list", "head", "tail"){

  override def evaluate(binding: Binding)(callback: => Boolean): Boolean = {
    val list = binding("list"); val head = binding("head"); val tail = binding("tail")
    if(list.isDefined && head.isDefined && tail.isDefined){
      list.resolve match {
        case l:List[?] =>
          tail.resolve match {
            case t:List[?] =>
              if(l == head.resolve :: t){
                callback
              }else true
            case _ => true
          }
        case _ => true
      }
    }else if(list.isEmpty && head.isDefined && tail.isDefined){
      tail.resolve match {
        case t: List[?] =>
          val l = head.resolve :: t
          binding.push{
            if(binding.merge("list", Value(l))) {
              callback
            }else true
          }
        case _ => true
      }
    } else if (list.isDefined) {
      list.resolve match {
        case l: List[?] =>
          l match {
            case h :: t =>
              binding.push{
                if( binding.merge("head", Value(h)) && binding.merge("tail", Value(t)) ){
                  callback
                }else true
              }
            case _ => true
          }
        case _ => true
      }
    }else true

  }

}
