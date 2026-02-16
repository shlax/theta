package org.theta.dsl

import org.theta.solver.{Atom, Clause, Database, Fact, Reference, Statement, Value}

import scala.annotation.targetName

object builder {

  def database(init: DatabaseBuilder ?=> Unit): Database = {
    given db : DatabaseBuilder = new DatabaseBuilder()
    init
    db.build()
  }

  def add(term: Clause*)(using b: DatabaseBuilder): Unit = {
    for(t <- term) b.add(t)
  }

  def fact(relation:String, parameters:(String, Value)*)(using b: DatabaseBuilder): Unit = {
    b.add(Fact(relation, parameters.toMap))
  }

  def fact(relation:String, parameters:Map[String, Value])(using b: DatabaseBuilder): Unit = {
    b.add(Fact(relation, parameters))
  }

  @targetName("ruleOperator")
  def rule(relation:String, parameters:(String, String)*)(init: RuleBuilder ?=> Unit)(using b: DatabaseBuilder): Unit = {
    given rb: RuleBuilder = new RuleBuilder(relation, parameters.map( (x, y) => x -> Reference(y) ).toMap)
    init
    b.add(rb.build())
  }

  def rule(relation:String, parameters:(String, Atom)*)(init: RuleBuilder ?=> Unit)(using b: DatabaseBuilder): Unit = {
    given rb: RuleBuilder = new RuleBuilder(relation, parameters.toMap)
    init
    b.add(rb.build())
  }

  def rule(relation:String, parameters:Map[String, Atom])(init: RuleBuilder ?=> Unit)(using b: DatabaseBuilder): Unit = {
    given rb : RuleBuilder = new RuleBuilder(relation, parameters)
    init
    b.add(rb.build())
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

}
