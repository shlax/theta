package org.theta

class Database extends Queryable {

  var rules: List[Fact] = Nil

  def add(rule: Fact): this.type = {
    rules = rule :: rules
    this
  }

  override def query(test: Fact => Boolean): List[Fact] = {
    rules.filter(test)
  }

}
