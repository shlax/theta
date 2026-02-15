package org.theta

object Binding {

  def apply(arguments:Map[String, Variable], parent:Binding): Binding = {
    new Binding(arguments, parent.database )
  }

  def apply(arguments:Map[String, Variable], database: Database): Binding = {
    new Binding(arguments, database)
  }

}

class Binding(val state:Map[String, Variable], val database: Database) extends Queryable {

  def push(block : => Unit): Unit = {
    val values = state.map { (key, value) =>
      (key, value.value)
    }

    block

    for((key, value) <- values){
      state(key).value = value
    }
  }

  def merge(key:String, value: Value): Boolean = {
    state.get(key) match {
      case Some(v) => v.set(value)
      case None => false
    }
  }

  def apply(key:String): Variable = state(key)

  override def query(test: Term => Boolean): Iterable[Term] = {
    database.query(test)
  }

}
