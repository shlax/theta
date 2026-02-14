package org.theta

trait Term {

  def evaluate(binding: Binding)(callback : => Unit): Unit 
  
}
