package org.theta.solver

import scala.annotation.targetName
import scala.collection.mutable.ArrayBuffer

object Database {

  class DatabaseBuilder {
    var buffer: ArrayBuffer[Clause] = new ArrayBuffer()

    def clauses: List[Clause] = buffer.toList

    def add(term: Clause): Unit = {
      buffer += term
    }
  }

  def database(init: DatabaseBuilder ?=> Unit): Database = {
    given db : DatabaseBuilder = new DatabaseBuilder()
    init
    Database(db.clauses)
  }

  def add(term: Clause*)(using b: DatabaseBuilder): Unit = {
    for(t <- term) b.add(t)
  }

  def fact(relation:String, parameters:Map[String, Value])(using b: DatabaseBuilder): Unit = {
    b.add(Fact(relation, parameters))
  }

  class RuleBuilder {
    var buffer: ArrayBuffer[Statement] = ArrayBuffer()

    def statements: List[Statement] = buffer.toList

    def add(statement: Statement): Unit = {
      buffer += statement
    }
  }

  @targetName("ruleOperator")
  def rule(relation:String, parameters:(String, String)*)(init: RuleBuilder ?=> Unit)(using tb: DatabaseBuilder): Unit = {
    given rb: RuleBuilder = new RuleBuilder()
    init
    tb.add(Rule(relation, parameters.map( (x, y) => x -> Reference(y) ).toMap, rb.statements))
  }

  def rule(relation:String, parameters:(String, Atom)*)(init: RuleBuilder ?=> Unit)(using tb: DatabaseBuilder): Unit = {
    given rb: RuleBuilder = new RuleBuilder()
    init
    tb.add(Rule(relation, parameters.toMap, rb.statements))
  }

  def rule(relation:String, parameters:Map[String, Atom])(init: RuleBuilder ?=> Unit)(using tb: DatabaseBuilder): Unit = {
    given rb : RuleBuilder = new RuleBuilder()
    init
    tb.add(Rule(relation, parameters, rb.statements))
  }

  def add(statement: Statement)(using b: RuleBuilder): Unit = {
    b.add(statement)
  }

  @targetName("statementOperator")
  def statement(relation: String, arguments: (String, String)*)(using b: RuleBuilder): Unit = {
    b.add(Statement(relation, arguments.map( (x, y) => x -> Reference(y) ).toMap))
  }

  def statement(relation: String, arguments: (String, Atom)*)(using b: RuleBuilder): Unit = {
    b.add(Statement(relation, arguments.toMap))
  }

  def statement(relation:String, arguments:Map[String, Atom])(using b: RuleBuilder): Unit = {
    b.add(Statement(relation, arguments))
  }

  def apply(t: Clause *): Database = {
    new Database(t)
  }

  def apply(l: Iterable[Clause]): Database = {
    new Database(l)
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
