package org.theta

object Binding {

  def apply(arguments:Map[String, Variable], onMatch: => Unit): Binding = {
    new Binding(arguments, { () => onMatch } )
  }

}

class Binding(val arguments:Map[String, Variable], val onMatch: () => Unit, val parent:Option[Binding] = None) {

  def push(block: => Unit): Unit = {
    val values = arguments.map { (key, value) =>
      (key, value.value)
    }

    block

    for((key, value) <- values){
      arguments(key).value = value
    }
  }

  def map(fn: => Unit): Binding = {
    new Binding(arguments, { () => fn }, Some(this) )
  }

  def merge(key:String, value: Value): Boolean = {
    arguments.get(key) match {
      case Some(v) => v.set(value)
      case None => false
    }
  }

  def solved: Boolean = {
    arguments.forall( _._2.value.isDefined ) && parent.forall( _.solved)
  }

}
