package org.theta

trait Queryable {

  def query(test: Rule => Boolean): List[Rule]

}
