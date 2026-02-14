package org.theta

class Binding(val arguments:Map[String, Atom], val onMatch: () => Unit) {
  def size: Int = arguments.size

  def push(block: => Unit): Unit = {
    val values: Map[String, Option[Any]] = arguments.flatMap { (key, value) =>
      if(value.save) Some(key, value.get) else None
    }

    block

    for((key, value) <- values){
      arguments(key).restore(value)
    }
  }

  def merge(key:String, value: Atom): Boolean = {
    arguments.get(key) match {
      case Some(v) => v.set(value)
      case None => false
    }
  }

}
