package org.theta

object Binding {

  def apply(arguments:Map[String, Variable], onMatch: => Unit): Binding = {
    new Binding(arguments, { () => onMatch } )
  }

}

class Binding(val arguments:Map[String, Variable], val onMatch: () => Unit) {

  def push(block: => Unit): Unit = {
    val values: Map[String, Option[Any]] = arguments.map { (key, value) =>
      (key, value.value)
    }

    block

    for((key, value) <- values){
      arguments(key).value  = value
    }
  }

  def map(fn: => Unit): Binding = new Binding(arguments, { () => fn } )

  def merge(key:String, value: Value): Boolean = {
    arguments.get(key) match {
      case Some(v) => v.set(value)
      case None => false
    }
  }

}
