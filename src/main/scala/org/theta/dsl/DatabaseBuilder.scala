package org.theta.dsl

import org.theta.solver.{Clause, Database}

import scala.collection.mutable.ArrayBuffer

class DatabaseBuilder {
  var buffer: ArrayBuffer[Clause] = new ArrayBuffer()

  def add(term: Clause): Unit = {
    buffer += term
  }

  def build(): Database = {
    Database(buffer)
  }

}
