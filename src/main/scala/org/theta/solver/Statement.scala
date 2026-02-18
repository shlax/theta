package org.theta.solver

trait Statement extends Term{

  def variables: Set[String]
  
}
