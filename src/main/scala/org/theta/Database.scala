package org.theta

class Database extends Queryable {

  var rules: List[Rule] = Nil

  def add(rule: Rule): this.type = {
    rules = rule :: rules
    this
  }

  override def query(test: Rule => Boolean): List[Rule] = {
    rules.filter(test)
  }

}
