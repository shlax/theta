package org.theta

class Binding(val id:String, val arguments:Map[String, Atom]) {
  def size: Int = arguments.size

  def state: State = new State

  class State {
    val values: Map[String, Option[Any]] = arguments.flatMap { (key, value) =>
      if(value.save) Some(key, value.get) else None
    }

    def restore(): Unit = {
      for((key, value) <- values){
        arguments(key).restore(value)
      }
    }
  }

  def merge(key:String, value: Atom): Boolean = {
    arguments.get(key) match {
      case Some(v) => v.set(value)
      case None => false
    }
  }

}
