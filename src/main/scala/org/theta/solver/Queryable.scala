package org.theta.solver

trait Queryable {

  def query(test: Clause => Boolean): Iterable[Clause]

}
