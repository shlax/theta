package org.theta.solver

trait Term {

  def arguments: Set[String]

  /** @return true if execution should continue */
  def evaluate(binding: Binding)(callback : => Boolean): Boolean

}