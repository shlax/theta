package org.theta

trait Atom{

  def get: Option[Any]

  def set(v:Atom):Boolean

  def save:Boolean = false

  def restore(v:Option[Any]):Unit = {}
}
