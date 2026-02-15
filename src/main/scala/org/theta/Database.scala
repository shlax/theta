package org.theta

import scala.annotation.targetName

object Database {

  @targetName("from")
  def apply(t: Term *): Database = {
    new Database(t.toList)
  }

  def apply(l: Seq[Term]): Database = {
    new Database(l)
  }

}

class Database(val terms: Seq[Term]) extends Queryable{

  override def query(test: Term => Boolean): Seq[Term] = {
    terms.filter(test)
  }

}
