package org.theta

object Database {

  class DatabaseBuilder {
    var terms: List[Term] = Nil

    def add(term: Term): Unit = {
      terms = term :: terms
    }
  }

  def apply(init: DatabaseBuilder ?=> Unit): Database = {
    given db : DatabaseBuilder = new DatabaseBuilder()
    init
    Database(db.terms)
  }

  def add(term: Term)(using tb: DatabaseBuilder): Unit = {
    tb.add(term)
  }

  def fact(relation:String, parameters:Map[String, Value])(using tb: DatabaseBuilder): Unit = {
    tb.add(Fact(relation, parameters))
  }

  class RuleBuilder {
    var statements: List[Statement] = Nil

    def add(statement: Statement): Unit = {
      statements = statement :: statements
    }
  }

  def rule(relation:String, parameters:Map[String, Atom])(init: RuleBuilder ?=> Unit)(using tb: DatabaseBuilder): Unit = {
    given rb : RuleBuilder = new RuleBuilder()
    init
    tb.add(Rule(relation, parameters, rb.statements))
  }

  def add(statement: Statement)(using tb: RuleBuilder): Unit = {
    tb.add(statement)
  }

  def statement(relation:String, arguments:Map[String, Atom])(using tb: RuleBuilder): Unit = {
    tb.add(Statement(relation, arguments))
  }

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
