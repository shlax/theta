package org.theta.solver

object Database {

  def apply(t: Clause *): Database = {
    new Database(t)
  }

  def apply(i: Iterable[Clause]): Database = {
    new Database(i)
  }

}

class Database(val clauses: Iterable[Clause]) extends Queryable{

  override def query(test: Clause => Boolean): Iterable[Clause] = {
    clauses.filter(test)
  }

  def query(relation:String, binding: Binding)(callback : => Unit):Unit = {
    val arguments = binding.state.keys
    for(candidate <- query(c => c.relation == relation && c.arguments == arguments)){
      binding.push {
        candidate.evaluate(binding)(callback)
      }
    }
  }

}
