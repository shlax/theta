package org.theta.core

class NotEqual extends Equal{

  override def isEqual(x: Any, y: Any): Boolean = {
    x != y
  }

}
