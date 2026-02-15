package org.theta

trait Queryable {

  def query(test: Clause => Boolean): Iterable[Clause]

}
