package org.theta.solver

object Binding {

  def apply(arguments:Map[String, Variable], parent:Binding): Binding = {
    new Binding(arguments, parent.database )
  }

  def apply(arguments:Map[String, Variable], database: Database): Binding = {
    new Binding(arguments, database)
  }

}

class Binding(val state:Map[String, Variable], val database: Database) extends Queryable {

  def push[T](block : => T): T = {
    val values = state.map { (key, value) =>
      (key, value.value)
    }

    val res:T = block

    for((key, value) <- values){
      state(key).value = value
    }

    res
  }

  def merge(key:String, value: Value): Boolean = {
    state(key).set(value)
  }

  def apply(key:String): Variable = state(key)

  override def query(test: Clause => Boolean): Iterable[Clause] = {
    database.query(test)
  }

}
