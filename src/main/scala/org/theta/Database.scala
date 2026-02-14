package org.theta

class Database extends Queryable{

  var terms: List[Term] = Nil

  def add(rule: Term): this.type = {
    terms = rule :: terms
    this
  }

  override def query(test: Term => Boolean): List[Term] = {
    terms.filter(test)
  }

}
