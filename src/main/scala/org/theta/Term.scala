package org.theta

trait Term {

  def evaluate(binding: Binding): Unit 
  
}
