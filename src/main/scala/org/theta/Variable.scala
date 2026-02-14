package org.theta

class Variable extends Atom {
  var value:Option[Any] = None

  def set(v:Atom):Boolean = value match {
    case s:Some[Any] => s == v.get
    case None =>
      value = v.get
      true
  }

  override def save: Boolean = true

  override def restore(v: Option[Any]): Unit = {
    value = v
  }

  def resolve: Any = value.get

  override def get: Option[Any] = value
}
