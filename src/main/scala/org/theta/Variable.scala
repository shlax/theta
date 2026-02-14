package org.theta

object Variable {

  def apply():Variable = {
    new Variable()
  }

  def apply(v:Any):Variable ={
    new Variable(Some(v))
  }

}

class Variable(v:Option[Any] = None){
  var value:Option[Any] = v

  def set(v:Value):Boolean = value match {
    case Some(x) => x == v.value
    case None =>
      value = Some(v.value)
      true
  }

  def resolve: Any = value.get

}
