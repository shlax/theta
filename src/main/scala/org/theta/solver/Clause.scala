package org.theta.solver

trait Clause extends Term {

  def relation:String

  def arguments:Set[String]

}
