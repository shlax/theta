package org.theta

case class Value(value:Any) extends Atom{

  override def get: Option[Any] = Some(value)

  override def set(v: Atom): Boolean = v.get match {
    case Some(v) => value == v
    case None => false
  }

}
