package org.theta

class Database{

  var terms: List[Term] = Nil

  def add(rule: Term): this.type = {
    terms = rule :: terms
    this
  }

  def query(test: Term => Boolean): List[Term] = {
    terms.filter(test)
  }

}
