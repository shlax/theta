package org.theta

trait Queryable {

  def query(test: Term => Boolean): Iterable[Term]

}
