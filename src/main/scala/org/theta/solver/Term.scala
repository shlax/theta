package org.theta.solver

trait Term {

  def relation:String

  def arguments:Set[String]

  def evaluate(binding: Binding)(callback : => Unit): Unit

}