package org.theta

trait Queryable {

  def query(test: Term => Boolean): List[Term]

}
