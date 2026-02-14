package org.theta

trait Term {

  def relation:String
  
  def evaluate(binding: Binding)(callback : => Unit): Unit 
  
}
