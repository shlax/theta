package org.theta.solver

trait Term {

  def relation: String

  def arguments: Set[String]
  
  /** @return true if execution should continue */
  def evaluate(binding: Binding)(callback : => Boolean): Boolean

}