package org.theta

object Binding {

  def apply(arguments:Map[String, Variable], onMatch: => Unit): Binding = {
    new Binding(arguments, { () => onMatch } )
  }

}

class Binding(val state:Map[String, Variable], val onMatch: () => Unit, val parent:Option[Binding] = None) {

  def push(block: => Unit): Unit = {
    val values = state.map { (key, value) =>
      (key, value.value)
    }

    block

    for((key, value) <- values){
      state(key).value = value
    }
  }

  def map(fn: => Unit): Binding = {
    new Binding(state, { () => fn }, Some(this) )
  }

  def merge(key:String, value: Value): Boolean = {
    state.get(key) match {
      case Some(v) => v.set(value)
      case None => false
    }
  }

  def solved: Boolean = {
    state.forall( _._2.value.isDefined ) && parent.forall( _.solved )
  }

}
