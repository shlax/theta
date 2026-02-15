package org.theta

object Database {

  def apply(t: Term *): Database = {
    new Database(t)
  }

  def apply(l: Iterable[Term]): Database = {
    new Database(l)
  }

}

class Database(val terms: Iterable[Term]) extends Queryable{

  override def query(test: Term => Boolean): Iterable[Term] = {
    terms.filter(test)
  }

}
