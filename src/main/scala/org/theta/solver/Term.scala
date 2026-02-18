package org.theta.solver

trait Term {

  /** @return true if execution should continue */
  def evaluate(binding: Binding)(callback : => Boolean): Boolean

}