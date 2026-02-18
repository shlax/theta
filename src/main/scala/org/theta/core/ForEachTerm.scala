//package org.theta.core
//
//import org.theta.solver.{Binding, Statement, Term}
//
//class ForEachTerm(val terms:Iterable[Statement]) extends Statement{
//
//  override def evaluate(binding: Binding)(callback: => Boolean): Boolean = {
//    var continue = true
//    for(t <- terms if continue){
//      continue = t.evaluate(binding)(callback)
//    }
//    continue
//  }
//
//}
