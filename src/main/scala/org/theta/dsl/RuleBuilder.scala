package org.theta.dsl

import org.theta.solver.{Atom, Rule, Statement}

import scala.collection.mutable.ArrayBuffer

class RuleBuilder(relation:String, parameters:Map[String, Atom]) {
  var buffer: ArrayBuffer[Statement] = ArrayBuffer()

  def build(): Rule = {
    Rule(relation, parameters, buffer.toList)
  }

  def add(statement: Statement): Unit = {
    buffer += statement
  }
}
