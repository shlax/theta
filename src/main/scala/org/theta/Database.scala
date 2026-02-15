package org.theta

class Database(t:Term *) extends Queryable{

  var terms: List[Term] = t.toList

  def += (rule: Term): this.type = {
    terms = rule :: terms
    this
  }

  override def query(test: Term => Boolean): List[Term] = {
    terms.filter(test)
  }

}
